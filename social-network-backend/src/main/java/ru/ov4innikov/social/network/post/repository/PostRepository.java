package ru.ov4innikov.social.network.post.repository;

import ru.ov4innikov.social.network.model.Post;

import java.math.BigDecimal;
import java.util.List;

public interface PostRepository {

    String create(String userId, String text);

    boolean deleteById(String id);

    List<Post> getFeed(Long offset, Long limit);

    List<Post> getFeed(String currentUserId, boolean forceUpdate);

    Post getById(String id);

    boolean update(String id, String text);

    long clean();
}
