package ru.ov4innikov.social.network.post.service;

import ru.ov4innikov.social.network.model.Post;

import java.math.BigDecimal;
import java.util.List;

public interface PostService {

    String create(String text);
    boolean deleteById(String id);
    List<Post> getFeed(Long offset, Long limit);
    Post getById(String id);
    boolean update(String id, String text);
}