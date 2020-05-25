package com.baoxinhai.community.dto;

import com.baoxinhai.community.model.User;
import lombok.Data;

@Data
public class CommentQueryDTO {

    private Integer id;
    private Integer parentId;
    private Integer type;
    private String commentator;
    private Long gmtCreate;
    private Long gmtModified;
    private String content;
    private Integer likeCount;
    private User user;
    private Integer commentCount;
}
