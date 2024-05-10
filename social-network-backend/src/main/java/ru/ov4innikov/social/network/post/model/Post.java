package ru.ov4innikov.social.network.post.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Post implements Serializable {

    private String id;
    private String text;
    private String authorUserId;
}
