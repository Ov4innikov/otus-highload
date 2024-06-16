package ru.ov4innikov.social.network.post.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import ru.ov4innikov.social.network.friend.service.FriendService;
import ru.ov4innikov.social.network.model.Post;
import ru.ov4innikov.social.network.post.handler.WebSocketConnectionHandler;
import ru.ov4innikov.social.network.post.service.PostService;

import java.util.List;
import java.util.function.Consumer;

@Log4j2
@RequiredArgsConstructor
@Component
public class FriendsPostChangingListener implements Consumer<String> {

    private final PostService postService;
    private final FriendService friendService;
    private final WebSocketConnectionHandler webSocketConnectionHandler;
    private final ObjectMapper objectMapper;

    @Override
    public void accept(String postId) {
        log.trace("postId = {}", postId);
        Post post = postService.getById(postId);
        String authorUserId = post.getAuthorUserId().orElse(null);
        List<String> friendIds = friendService.getFriendIds(authorUserId);
        log.trace("Friends of user {} : {}", authorUserId, friendIds);
        friendIds.forEach(friendId -> {
            ru.ov4innikov.social.network.async.post.model.Post postAsync = ru.ov4innikov.social.network.async.post.model.Post.builder()
                    .postId(post.getId().orElse(null))
                    .postText(post.getText().orElse(null))
                    .author_user_id(post.getAuthorUserId().orElse(null))
                    .build();
            try {
                webSocketConnectionHandler.sendToUser(friendId, objectMapper.writeValueAsString(postAsync));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
