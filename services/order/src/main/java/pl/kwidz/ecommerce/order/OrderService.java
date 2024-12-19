package pl.kwidz.ecommerce.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.kwidz.ecommerce.customer.CustomerClient;
import pl.kwidz.ecommerce.exception.BusinessException;
import pl.kwidz.ecommerce.orderline.OrderLineRequest;
import pl.kwidz.ecommerce.orderline.OrderLineService;
import pl.kwidz.ecommerce.product.ProductClient;
import pl.kwidz.ecommerce.product.PurchaseRequest;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final CustomerClient customerClient;
    private final ProductClient productClient;
    private final OrderLineService orderLineService;
    private final OrderRepository repository;
    private final OrderMapper mapper;

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
        // todo start payment process

        // send the order confirmation -> notification-ms (kafka)
        return order.getId();
    }
}
