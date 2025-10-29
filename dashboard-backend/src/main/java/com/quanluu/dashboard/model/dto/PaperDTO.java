package com.quanluu.dashboard.model.dto;

import lombok.Data;
import java.util.Set;

@Data
public class PaperDTO {
    private Long id;
    private String title;
    private String body;
    private String summary;
    private Set<Long> tagIds;

    private String authors;
    private String venue;
    private Integer year;
    private String doi;
    private String citationKey;
    private String pdfUrl;
    private String myNotes;
}
