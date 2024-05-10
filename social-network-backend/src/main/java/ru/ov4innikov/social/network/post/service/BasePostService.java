package ru.ov4innikov.social.network.post.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import ru.ov4innikov.social.network.model.Post;
import ru.ov4innikov.social.network.model.User;
import ru.ov4innikov.social.network.post.repository.PostRepository;
import ru.ov4innikov.social.network.user.service.UserService;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Log4j2
@RequiredArgsConstructor
@Service
public class BasePostService implements PostService {

    private final PostRepository postRepository;
    private final UserService userService;

    @Override
    public String create(String text) {
        User currentUser = userService.getCurrentUser();
        if (currentUser.getId().isEmpty()) {
            throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return postRepository.create(currentUser.getId().get(), text);
    }

    @Override
    public boolean deleteById(String id) {
        return postRepository.deleteById(id);
    }

    @Override
    public List<Post> getFeed(Long offset, Long limit) {
        User currentUser = userService.getCurrentUser();
        if (currentUser.getId().isEmpty()) {
            throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return Optional
                .ofNullable(postRepository.getFeed(currentUser.getId().get()))
                .orElse(Collections.emptyList())
                .stream()
                .map(post -> new Post()
                        .id(post.getId())
                        .authorUserId(post.getAuthorUserId())
                        .text(post.getText()))
                .toList();
    }

    @Override
    public Post getById(String id) {
        ru.ov4innikov.social.network.post.model.Post byId = postRepository.getById(id);
        return new Post()
                .id(byId.getId())
                .authorUserId(byId.getAuthorUserId())
                .text(byId.getText());
    }

    @Override
    public boolean update(String id, String text) {
        return postRepository.update(id, text);
    }
}
