package pl.kwidz.ecommerce.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.ClientHttpRequestFactories;
import org.springframework.boot.web.client.ClientHttpRequestFactorySettings;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import pl.kwidz.ecommerce.customer.CustomerClient;
import pl.kwidz.ecommerce.payment.PaymentClient;
import pl.kwidz.ecommerce.product.ProductClient;

import java.time.Duration;

@Configuration
public class RestClientConfig {

    @Value("${application.config.customer-url}")
    private String customerUrl;
    @Value("${application.config.product-url}")
    private String productUrl;
    @Value("${application.config.payment-url}")
    private String paymentUrl;

    @Bean
    public CustomerClient customerClient() {
        RestClient restClient = RestClient.builder()
                .baseUrl(customerUrl)
                .requestFactory(getClientRequestFactory())
                .build();
        RestClientAdapter restClientAdapter = RestClientAdapter.create(restClient);
        HttpServiceProxyFactory httpServiceProxyFactory = HttpServiceProxyFactory.builderFor(restClientAdapter).build();
        return httpServiceProxyFactory.createClient(CustomerClient.class);
    }

    @Bean
    public ProductClient productClient() {
        RestClient restClient = RestClient.builder()
                .baseUrl(productUrl)
                .requestFactory(getClientRequestFactory())
                .build();
        RestClientAdapter restClientAdapter = RestClientAdapter.create(restClient);
        HttpServiceProxyFactory httpServiceProxyFactory = HttpServiceProxyFactory.builderFor(restClientAdapter).build();
        return httpServiceProxyFactory.createClient(ProductClient.class);
    }

    @Bean
    public PaymentClient paymentClient() {
        RestClient restClient = RestClient.builder()
                .baseUrl(paymentUrl)
                .requestFactory(getClientRequestFactory())
                .build();
        RestClientAdapter restClientAdapter = RestClientAdapter.create(restClient);
        HttpServiceProxyFactory httpServiceProxyFactory = HttpServiceProxyFactory.builderFor(restClientAdapter).build();
        return httpServiceProxyFactory.createClient(PaymentClient.class);
    }

    private ClientHttpRequestFactory getClientRequestFactory() {
        ClientHttpRequestFactorySettings clientHttpRequestFactorySettings = ClientHttpRequestFactorySettings.DEFAULTS
                .withConnectTimeout(Duration.ofSeconds(3))
                .withReadTimeout(Duration.ofSeconds(3));
        return ClientHttpRequestFactories.get(clientHttpRequestFactorySettings);
    }
}
