package ru.ov4innikov.social.network.friend.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "app.friend")
public class AppFriendProperties {

    private String friendChangingQueue = "friendChanging";
    private String friendChangingExchange = "friendChanging";
    private String friendChangingRoutingKey = "friendChanging";
}
