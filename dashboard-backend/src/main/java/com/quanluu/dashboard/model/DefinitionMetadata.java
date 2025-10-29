package com.quanluu.dashboard.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * Metadata for definition-type content.
 *
 * Stores additional information specific to concept definitions,
 * including the term being defined and its field.
 *
 * Fields:
 * - term: The term or concept being defined
 * - field: Academic field (e.g., "Complexity Theory", "Algorithms")
 * - formalDefinition: Formal mathematical definition (LaTeX supported)
 * - relatedConcepts: Comma-separated list of related concept names
 */
@Entity
@Table(name = "definition_metadata")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class DefinitionMetadata extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "content_id", nullable = false, unique = true)
    private Content content;

    @Column(nullable = false, length = 200)
    private String term;

    @Column(length = 200)
    private String field;

    @Column(columnDefinition = "TEXT")
    private String formalDefinition;

    @Column(length = 500)
    private String relatedConcepts;
}
