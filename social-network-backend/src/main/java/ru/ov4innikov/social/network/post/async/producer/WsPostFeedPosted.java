package ru.ov4innikov.social.network.post.async.producer;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import ru.ov4innikov.social.network.async.post.model.Post;
import ru.ov4innikov.social.network.async.post.producer.IPostFeedPosted;

@Log4j2
@Component
public class WsPostFeedPosted implements IPostFeedPosted {

    @Override
    public Post postFeedPosted() {
        log.trace("postFeedPosted");
        return null;
    }
}
