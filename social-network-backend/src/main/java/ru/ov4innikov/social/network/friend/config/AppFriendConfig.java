package ru.ov4innikov.social.network.friend.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import ru.ov4innikov.social.network.friend.properties.AppFriendProperties;

@EnableConfigurationProperties(AppFriendProperties.class)
@Configuration
public class AppFriendConfig {

}
