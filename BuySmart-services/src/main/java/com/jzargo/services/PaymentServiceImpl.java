package com.jzargo.services;

import com.jzargo.dto.PaymentCreateAndUpdateDto;
import com.jzargo.dto.PaymentReadDto;
import com.jzargo.entity.Order;
import com.jzargo.exceptions.DataNotFoundException;
import com.jzargo.mapper.PaymentCreateAndUpdateMapper;
import com.jzargo.mapper.PaymentReadMapper;
import com.jzargo.repository.OrderRepository;
import com.jzargo.repository.PaymentRepository;
import com.jzargo.repository.UserRepository;
import com.stripe.Stripe;
import com.stripe.model.Customer;
import com.stripe.model.checkout.Session;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PaymentServiceImpl implements PaymentService{
    @Value("${stripe.api.key}")
    private String apiKey;
    private final PaymentReadMapper paymentReadMapper;
    private final PaymentRepository paymentRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final PaymentCreateAndUpdateMapper paymentCreateAndUpdateMapper;
    @Value("${ngrok.url}")
    private String PublicUrl;

    public PaymentServiceImpl(PaymentReadMapper paymentReadMapper, PaymentRepository paymentRepository, UserRepository userRepository, OrderRepository orderRepository, PaymentCreateAndUpdateMapper paymentCreateAndUpdateMapper){
        this.paymentReadMapper = paymentReadMapper;
        this.paymentRepository = paymentRepository;
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
        this.paymentCreateAndUpdateMapper = paymentCreateAndUpdateMapper;
    }

    @Override
    public Page<PaymentReadDto> findAllByUserId(Long userId, Pageable page) {
        return paymentRepository.findAllByUserId(userId, page)
                .map(paymentReadMapper::map);
    }

    @Override
    public PaymentReadDto findById(Long id) {
        return paymentRepository.findById(id)
                .map(paymentReadMapper::map)
                .orElseThrow();
    }

    @Override
    @SneakyThrows
    public String createCheckoutSession(Long userId, PaymentCreateAndUpdateDto dto) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new DataNotFoundException("User not found"));

        Stripe.apiKey= apiKey;

        if(user.getStripeCustomerId() == null){
            CustomerCreateParams customerParams = CustomerCreateParams.builder()
                    .setEmail(user.getEmail())
                    .setName(user.getUsername())
                    .build();
            Customer customer = Customer.create(customerParams);
            user.setStripeCustomerId(customer.getId());
        }

        SessionCreateParams order = SessionCreateParams.builder().addAllPaymentMethodType(List.of(
                        SessionCreateParams.PaymentMethodType.CARD,
                        SessionCreateParams.PaymentMethodType.PAYPAL,
                        SessionCreateParams.PaymentMethodType.PAY_BY_BANK)
                )
                .setCustomer(user.getStripeCustomerId())
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl(PublicUrl+"/success/"+dto.getOrderId())
                .setCancelUrl(PublicUrl+"/cancel/"+dto.getOrderId())
                .addLineItem(SessionCreateParams.LineItem.builder()
                        .setQuantity(
                                (long) orderRepository.findById(dto.getOrderId())
                                        .map(Order::getQuantity)
                                        .orElseThrow()
                        )
                        .setPriceData(
                                SessionCreateParams.LineItem.PriceData.builder()
                                        .setCurrency(dto.getCurrency())
                                        .setProductData(
                                                SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                        .setName("Order")
                                                        .build())
                                        .setUnitAmount((long) (dto.getAmount() * 100.0))
                                        .build()
                        )
                        .build()
                )
                .build();
        Session session = Session.create(order);
        var payment = paymentCreateAndUpdateMapper.map(dto);
        payment.setUser(user);
        payment.setStripePaymentId(session.getId());

        paymentRepository.saveAndFlush(payment);
        return session.getUrl();
    }
}
