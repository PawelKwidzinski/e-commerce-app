package pl.kwidz.ecommerce.order;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.kwidz.ecommerce.customer.CustomerClient;
import pl.kwidz.ecommerce.exception.BusinessException;
import pl.kwidz.ecommerce.kafka.OrderConfirmation;
import pl.kwidz.ecommerce.kafka.OrderProducer;
import pl.kwidz.ecommerce.orderline.OrderLineRequest;
import pl.kwidz.ecommerce.orderline.OrderLineService;
import pl.kwidz.ecommerce.payment.PaymentClient;
import pl.kwidz.ecommerce.payment.PaymentRequest;
import pl.kwidz.ecommerce.product.ProductClient;
import pl.kwidz.ecommerce.product.PurchaseRequest;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final CustomerClient customerClient;
    private final ProductClient productClient;
    private final OrderLineService orderLineService;
    private final OrderRepository repository;
    private final OrderMapper mapper;
    private final OrderProducer orderProducer;
    private final PaymentClient paymentClient;

    public Integer createOrder(OrderRequest request) {
        // check customer
        var customer = customerClient.findCustomerById(request.customerId())
                .orElseThrow(() -> new BusinessException("Cannot create order: No customer exists with the provided ID"));

        // purchase the products -> product-ms
        var purchasedProducts = productClient.purchaseProducts(request.products());

        // persist order
        var order = repository.save(mapper.toOrder(request));

        // persist order lines
        for (PurchaseRequest purchaseRequest : request.products()) {
            orderLineService.saveOrderLine(
                    new OrderLineRequest(
                            null,
                            order.getId(),
                            purchaseRequest.productId(),
                            purchaseRequest.quantity()
                    )
            );
        }

        // payment process
        paymentClient.createPayment(new PaymentRequest(
           request.amount(), request.paymentMethod(), request.id(), request.reference(), customer
        ));

        // send the order confirmation -> notification-ms (kafka)
        orderProducer.sendOrderConfirmation(new OrderConfirmation(
                request.reference(),
                request.amount(),
                request.paymentMethod(),
                customer,
                purchasedProducts
        ));

        return order.getId();
    }

    public List<OrderResponse> findAll() {
        return repository.findAll().stream()
                .map(mapper::fromOrder)
                .collect(Collectors.toList());
    }

    public OrderResponse findById(Integer id) {
        return repository.findById(id)
                .map(mapper::fromOrder)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Order with ID: %d not found", id)));
    }

}
