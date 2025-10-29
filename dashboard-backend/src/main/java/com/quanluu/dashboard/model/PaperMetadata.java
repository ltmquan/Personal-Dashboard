package com.quanluu.dashboard.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * Metadata for paper-type content.
 *
 * Stores bibliographic information and notes about research papers.
 *
 * Fields:
 * - authors: Paper authors (comma-separated)
 * - venue: Publication venue (e.g., "STOC 2023", "Journal of ACM")
 * - year: Publication year
 * - doi: Digital Object Identifier
 * - citationKey: BibTeX citation key (e.g., "Smith2023")
 * - pdfUrl: URL to PDF file (if available)
 * - myNotes: Personal notes and insights about the paper
 */
@Entity
@Table(name = "paper_metadata")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class PaperMetadata extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "content_id", nullable = false, unique = true)
    private Content content;

    @Column(length = 500)
    private String authors;

    @Column(length = 200)
    private String venue;

    private Integer year;

    @Column(length = 200)
    private String doi;

    @Column(name = "citation_key", length = 100)
    private String citationKey;

    @Column(name = "pdf_url", length = 500)
    private String pdfUrl;

    @Column(name = "my_notes", columnDefinition = "TEXT")
    private String myNotes;
}
