package com.baoxinhai.community.model;

import lombok.Data;

@Data
public class Comment {
    private Integer id;
    private Integer parentId;
    private Integer type;
    private String commentator;
    private Long gmtCreate;
    private Long gmtModified;
    private String content;
    private Integer likeCount;
    private Integer commentCount;
}
