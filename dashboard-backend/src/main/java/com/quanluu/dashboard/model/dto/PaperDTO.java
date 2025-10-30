package com.quanluu.dashboard.model.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.Set;

@Data
public class PaperDTO {
    private Long id;
    private String title;
    private String body;
    private Set<Long> tagIds;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private String authors;
    private String venue;
    private Integer year;
    private String doi;
    private String citationKey;
    private String pdfUrl;
    private String myNotes;

    private Set<Long> referencedPaperIds;
    private Set<Long> referencedTheoremIds;
    private Set<Long> referencedDefinitionIds;
    private Set<Long> referencedPostIds;
}
