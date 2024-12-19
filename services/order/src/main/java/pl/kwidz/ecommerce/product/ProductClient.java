package pl.kwidz.ecommerce.product;

import org.springframework.web.service.annotation.PostExchange;

import java.util.List;

public interface ProductClient {

    @PostExchange("/purchase")
    List<PurchaseResponse> purchaseProducts(List<PurchaseRequest> requests);
}
