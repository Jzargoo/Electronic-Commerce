package services;

import Annotations.IT;
import com.jzargo.services.PaymentService;

@IT
public class PaymentServiceIntegrationTest {
    private final PaymentService paymentService;

    public PaymentServiceIntegrationTest(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    void createPayment(){
        paymentService.findById(1L);
    }
}
