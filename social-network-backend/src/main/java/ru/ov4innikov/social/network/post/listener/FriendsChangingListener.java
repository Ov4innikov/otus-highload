package ru.ov4innikov.social.network.post.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import ru.ov4innikov.social.network.post.repository.PostRepository;

import java.util.Arrays;

@Log4j2
@RequiredArgsConstructor
@Component
public class FriendsChangingListener {

    private final PostRepository postRepository;

//    @RabbitListener(queues = "friendsChanging")
    public void processMessage(Message message) {
        String currentUserId = Arrays.toString(message.getBody());
        postRepository.getFeed(currentUserId, true);
    }
}
