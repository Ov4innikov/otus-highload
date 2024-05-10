package ru.ov4innikov.social.network.post.config;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.core.*;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.ov4innikov.social.network.post.properties.AppPostProperties;

@Log4j2
@EnableConfigurationProperties(AppPostProperties.class)
@RequiredArgsConstructor
@Configuration
public class AppPostConfig {

    private final AppPostProperties appPostProperties;

    @PostConstruct
    public void init() {
        log.info("AppPostProperties = {}", appPostProperties);
    }

    @Bean
    public Exchange friendChangingExchange() {
        return ExchangeBuilder
                .directExchange(appPostProperties.getFriendChangingExchange())
                .durable(true)
                .build();
    }

    @Bean
    public Queue friendChangingQueue() {
        return QueueBuilder
                .durable(appPostProperties.getFriendChangingQueue())
                .build();
    }

    @Bean
    public Binding friendChangingBinding(Exchange exchange, Queue queue) {
        return BindingBuilder
                .bind(queue)
                .to(exchange)
                .with(appPostProperties.getFriendChangingRoutingKey())
                .noargs();
    }
}
