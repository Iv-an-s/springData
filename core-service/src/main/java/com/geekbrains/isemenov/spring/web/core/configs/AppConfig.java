package com.geekbrains.isemenov.spring.web.core.configs;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.tcp.TcpClient;

import java.util.concurrent.TimeUnit;

@Configuration
public class AppConfig {
    @Value("${integrations.cart-service.url}")
    private String cartServiceUrl;
//  - асинхронный клиент, можно послать запрос, и "пойти заниматься своими делами". Когда ответ придет -
//    тогда его и обработает. Начиная с 5-й версии Спринг поддерживает только WebClient, т.к. он может работать
//    как в синхронном так и в асинхронном режиме. От RestTemplate уходим к WebClient.
//    Если будет 10 микросервисов, с которыми мы общаемся - то будет 10 WebClient-ов.
    @Bean
    public WebClient cartServiceWebClient() {
//  WebClient будет устанавливать соединение с указанным хостом (в нашем случае это микросервис) по
//  сконфигурированным параметрам. Можем указать таймаут на connection, время ожидания чтения, время ожидания записи.
//  Если в указанное время ответ не получаем - просто получим Exception.
//  Конфигурация подключения:
        TcpClient tcpClient = TcpClient
                .create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 2000)
                .doOnConnected(connection -> {
                    connection.addHandlerLast(new ReadTimeoutHandler(10000, TimeUnit.MILLISECONDS));
                    connection.addHandlerLast(new WriteTimeoutHandler(2000, TimeUnit.MILLISECONDS));
                });
//  Собираем сам WebClient. Передаем ему путь, по которому он будет "стучаться".
        return WebClient
                .builder()
                .baseUrl(cartServiceUrl)
                .clientConnector(new ReactorClientHttpConnector(HttpClient.from(tcpClient)))
                .build();
    }
}
