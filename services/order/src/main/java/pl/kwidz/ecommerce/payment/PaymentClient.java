package pl.kwidz.ecommerce.payment;

import org.springframework.web.service.annotation.PostExchange;

public interface PaymentClient {

    @PostExchange
    Integer createPayment(PaymentRequest request);
}
