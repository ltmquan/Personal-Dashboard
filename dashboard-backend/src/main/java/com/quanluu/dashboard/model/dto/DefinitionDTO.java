package com.quanluu.dashboard.model.dto;

import lombok.Data;
import java.util.Set;

@Data
public class DefinitionDTO {
    private Long id;
    private String title;
    private String body;
    private String summary;
    private Set<Long> tagIds;

    private String term;
    private String field;
    private String formalDefinition;
    private String relatedConcepts;
}
