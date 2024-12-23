package pl.kwidz.ecommerce.payment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.kwidz.ecommerce.notification.NotificationProducer;
import pl.kwidz.ecommerce.notification.PaymentNotificationRequest;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository repository;
    private final PaymentMapper mapper;
    private final NotificationProducer notificationProducer;

    public Integer createPayment(PaymentRequest request) {
        var payment = repository.save(this.mapper.toPayment(request));

        notificationProducer.sendNotification(
                new PaymentNotificationRequest(
                        request.orderReference(),
                        request.amount(),
                        request.paymentMethod(),
                        request.customer().firstname(),
                        request.customer().lastname(),
                        request.customer().email()
                )
        );
        return payment.getId();
    }
}
