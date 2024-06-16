package ru.ov4innikov.social.network.post.config;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
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

}
