package pl.kwidz.ecommerce.kafka;

import pl.kwidz.ecommerce.customer.CustomerResponse;
import pl.kwidz.ecommerce.order.PaymentMethod;
import pl.kwidz.ecommerce.product.PurchaseResponse;

import java.math.BigDecimal;
import java.util.List;

public record OrderConfirmation(
        String orderReference,
        BigDecimal totalAmount,
        PaymentMethod paymentMethod,
        CustomerResponse customer,
        List<PurchaseResponse> products
) {
}
