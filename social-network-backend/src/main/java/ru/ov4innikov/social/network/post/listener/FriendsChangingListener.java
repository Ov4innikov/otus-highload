package ru.ov4innikov.social.network.post.listener;

import io.micrometer.observation.annotation.Observed;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import ru.ov4innikov.social.network.post.repository.PostRepository;

import java.util.function.Consumer;

@Log4j2
@RequiredArgsConstructor
@Observed
@Component
public class FriendsChangingListener implements Consumer<String>{

    private final PostRepository postRepository;

    @Override
    public void accept(String currentUserId) {
        log.trace("accept FriendsChangingListener {}", currentUserId);
        postRepository.getFeedForce(currentUserId);
    }
}