package com.quanluu.dashboard.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * Metadata for theorem-type content.
 *
 * Stores additional information specific to mathematical theorems,
 * including formal statement, proof, and classification.
 *
 * Fields:
 * - statement: Formal theorem statement (LaTeX supported)
 * - proof: Theorem proof (LaTeX supported)
 * - field: Mathematical field (e.g., "Complexity Theory", "Graph Theory")
 */
@Entity
@Table(name = "theorem_metadata")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class TheoremMetadata extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "content_id", nullable = false, unique = true)
    private Content content;

    @Column(columnDefinition = "TEXT")
    private String statement;

    @Column(columnDefinition = "TEXT")
    private String proof;

    @Column(length = 200)
    private String field;
}
