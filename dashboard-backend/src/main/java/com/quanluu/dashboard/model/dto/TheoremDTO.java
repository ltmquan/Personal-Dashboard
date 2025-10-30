package com.quanluu.dashboard.model.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.Set;

@Data
public class TheoremDTO {
    private Long id;
    private String title;
    private String body;
    private Set<Long> tagIds;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private String statement;
    private String proof;
    private String field;

    private Set<Long> referencedPaperIds;
    private Set<Long> referencedTheoremIds;
    private Set<Long> referencedDefinitionIds;
    private Set<Long> referencedPostIds;
}
