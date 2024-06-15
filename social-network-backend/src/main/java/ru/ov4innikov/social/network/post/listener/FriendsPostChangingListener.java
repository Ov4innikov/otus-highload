package ru.ov4innikov.social.network.post.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import ru.ov4innikov.social.network.friend.service.FriendService;
import ru.ov4innikov.social.network.model.Post;
import ru.ov4innikov.social.network.post.service.PostService;

import java.util.List;
import java.util.function.Consumer;

@Log4j2
@RequiredArgsConstructor
@Component
public class FriendsPostChangingListener implements Consumer<String> {

    private final PostService postService;
    private final FriendService friendService;

    @Override
    public void accept(String postId) {
        log.trace("postId = {}", postId);
        Post post = postService.getById(postId);
        String authorUserId = post.getAuthorUserId().orElse(null);
        List<String> friendIds = friendService.getFriendIds(authorUserId);
        log.info("Friends of user {} : {}", authorUserId, friendIds);
    }
}
