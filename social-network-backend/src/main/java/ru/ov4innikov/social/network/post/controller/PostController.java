package ru.ov4innikov.social.network.post.controller;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
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
        return postCreatePostRequest
                .map(createPostRequest -> new ResponseEntity<>(postService.create(createPostRequest.getText()), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.OK));
    }

    @Override
    public ResponseEntity<Void> postDeleteIdPut(String id) {
        postService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Post>> postFeedGet(
            @Parameter(name = "offset", description = "", in = ParameterIn.QUERY) @Valid @RequestParam(value = "offset", required = false, defaultValue = "0") Optional<@DecimalMin("0") BigDecimal> offset,
            @Parameter(name = "limit", description = "", in = ParameterIn.QUERY) @Valid @RequestParam(value = "limit", required = false, defaultValue = "10") Optional<@DecimalMin("1") BigDecimal> limit
    ) {
        return new ResponseEntity<>(postService.getFeed(offset.orElse(BigDecimal.valueOf(0L)).longValue(), limit.orElse(BigDecimal.valueOf(100L)).longValue()), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Post> postGetIdGet(String id) {
        return new ResponseEntity<>(postService.getById(id), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> postUpdatePut(Optional<PostUpdatePutRequest> postUpdatePutRequest) {
        postUpdatePutRequest.ifPresent((post -> postService.update(post.getId(), post.getText())));
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
