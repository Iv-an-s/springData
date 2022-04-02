package com.geekbrains.isemenov.spring.web.core.services;

import com.geekbrains.isemenov.spring.web.api.exceptions.ResourceNotFoundException;
import com.geekbrains.isemenov.spring.web.core.entities.Order;
import com.paypal.orders.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PayPalService {
    private final OrderService orderService;

    @Transactional // сборка заказа
    public OrderRequest createOrderRequest(Long orderId) {
        com.geekbrains.isemenov.spring.web.core.entities.Order order = orderService.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Заказ не найден"));
        //помимо findById добавить что заказ в д.б. в статусе "не оплачен"

        OrderRequest orderRequest = new OrderRequest(); // формируем запрос на создание заказа на PayPal
        orderRequest.checkoutPaymentIntent("CAPTURE"); // какие бывают виды intent-ов смотрим в доках PayPal

        ApplicationContext applicationContext = new ApplicationContext() // указываем детали нашего приложения
                .brandName("Spring Web Market") // название приложения
                .landingPage("BILLING") // как выглядит страница ввода данных
                .shippingPreference("SET_PROVIDED_ADDRESS"); // детали доставки. "SET_PROVIDED.." - пользователь сам может указать куда доставлять
        orderRequest.applicationContext(applicationContext); // подшиваем информацию о приложении в запрос

        List<PurchaseUnitRequest> purchaseUnitRequests = new ArrayList<>(); // формируем список покупок (лист единиц покупок)
        PurchaseUnitRequest purchaseUnitRequest = new PurchaseUnitRequest()
                .referenceId(orderId.toString()) // то, к какому заказу относятся
                .description("Spring Web Market Order")
                .amountWithBreakdown(new AmountWithBreakdown().currencyCode("RUB").value(String.valueOf(order.getTotalPrice()))
                        .amountBreakdown(new AmountBreakdown().itemTotal(new Money().currencyCode("RUB").value(String.valueOf(order.getTotalPrice())))))
                // сумма платежа с указанием валюты.
                .items(order.getItems().stream() // из заказа достаем наши OrderItem-ы
                        .map(orderItem -> new Item() // преобразуем в то что понимает PayPal
                                .name(orderItem.getProduct().getTitle())
                                .unitAmount(new Money().currencyCode("RUB").value(String.valueOf(orderItem.getPrice())))
                                .quantity(String.valueOf(orderItem.getQuantity())))
                        .collect(Collectors.toList())) // собираем в лист
                .shippingDetail(new ShippingDetail().name(new Name().fullName(order.getUsername())) //собираем детали доставки
                        .addressPortable(new AddressPortable().addressLine1(order.getAddress())
                                .adminArea2(order.getCity()).countryCode("RU")));
                        // могут собираться из тех полей, которые мы заполняем
        purchaseUnitRequests.add(purchaseUnitRequest);
        orderRequest.purchaseUnits(purchaseUnitRequests); // пакуем в запрос
        return orderRequest;
    }
}
