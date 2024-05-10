package ru.ov4innikov.social.network.post.listener;

import io.micrometer.observation.annotation.Observed;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import ru.ov4innikov.social.network.post.repository.PostRepository;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

@Log4j2
@RequiredArgsConstructor
@Observed
@Component
public class FriendsChangingListener {

    private final PostRepository postRepository;

    @RabbitListener(queues = "${app.post.friendChangingQueue}")
    public void processMessage(Message message) {
        String currentUserId = new String(message.getBody(), StandardCharsets.UTF_8);
        postRepository.getFeedForce(currentUserId);
    }
}