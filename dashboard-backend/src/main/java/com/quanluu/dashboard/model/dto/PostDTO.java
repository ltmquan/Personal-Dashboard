package com.quanluu.dashboard.model.dto;

import lombok.Data;
import java.util.Set;

@Data
public class PostDTO {
    private Long id;
    private String title;
    private String body;
    private String summary;
    private Set<Long> tagIds;
}
