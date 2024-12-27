package pl.kwidz.ecommerce.product;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.PostExchange;

import java.util.List;

public interface ProductClient {

    @PostExchange("/purchase")
    List<PurchaseResponse> purchaseProducts(@RequestBody List<PurchaseRequest> requests);
}
