package ru.ov4innikov.social.network.post.controller;

import jakarta.validation.constraints.DecimalMin;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import ru.ov4innikov.social.network.api.server.PostApi;
import ru.ov4innikov.social.network.model.Post;
import ru.ov4innikov.social.network.model.PostCreatePostRequest;
import ru.ov4innikov.social.network.model.PostUpdatePutRequest;
import ru.ov4innikov.social.network.post.service.PostService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Log4j2
@RequiredArgsConstructor
@Controller
public class PostController implements PostApi {

    private final PostService postService;

    @Override
    public ResponseEntity<String> postCreatePost(Optional<PostCreatePostRequest> postCreatePostRequest) {
        return null;
    }

    @Override
    public ResponseEntity<Void> postDeleteIdPut(String id) {
        return null;
    }

    @Override
    public ResponseEntity<List<Post>> postFeedGet(Optional<@DecimalMin("0") BigDecimal> offset, Optional<@DecimalMin("1") BigDecimal> limit) {
        return null;
    }

    @Override
    public ResponseEntity<Post> postGetIdGet(String id) {
        return null;
    }

    @Override
    public ResponseEntity<Void> postUpdatePut(Optional<PostUpdatePutRequest> postUpdatePutRequest) {
        return null;
    }
}
