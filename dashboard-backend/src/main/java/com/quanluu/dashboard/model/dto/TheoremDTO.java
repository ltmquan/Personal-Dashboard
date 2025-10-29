package com.quanluu.dashboard.model.dto;

import lombok.Data;
import java.util.Set;

@Data
public class TheoremDTO {
    private Long id;
    private String title;
    private String body;
    private String summary;
    private Set<Long> tagIds;

    private String statement;
    private String proof;
    private String field;
}
