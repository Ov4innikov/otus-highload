package ru.ov4innikov.social.network.post.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "app.post")
public class AppPostProperties {

    private String friendChangingQueue = "friendChanging";
    private String friendChangingExchange = "friendChanging";
    private String friendChangingRoutingKey = "friendChanging";
}
