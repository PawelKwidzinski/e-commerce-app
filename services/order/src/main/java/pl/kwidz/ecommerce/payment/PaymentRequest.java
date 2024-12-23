package pl.kwidz.ecommerce.payment;

import pl.kwidz.ecommerce.customer.CustomerResponse;
import pl.kwidz.ecommerce.order.PaymentMethod;

import java.math.BigDecimal;

public record PaymentRequest(
        BigDecimal amount,
        PaymentMethod paymentMethod,
        Integer orderId,
        String orderReference,
        CustomerResponse customer
) {
}
