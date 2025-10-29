package com.quanluu.dashboard.model;

import com.quanluu.dashboard.constants.ContentType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.HashSet;
import java.util.Set;

/**
 * Content entity representing all types of academic content.
 *
 * This is the base entity for the hybrid content model. All content
 * (posts, theorems, definitions, papers, problems) are stored here
 * with type-specific metadata in separate tables.
 *
 * Fields:
 * - title: Content title
 * - body: Main content in markdown/LaTeX format
 * - contentType: Type of content (POST, THEOREM, DEFINITION, PAPER, PROBLEM)
 * - tags: Associated tags for categorization
 * - summary: Optional brief summary/abstract
 */
@Entity
@Table(name = "contents")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Content extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String body;

    @Enumerated(EnumType.STRING)
    @Column(name = "content_type", nullable = false)
    private ContentType contentType;

    @Column(columnDefinition = "TEXT")
    private String summary;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "content_tags",
        joinColumns = @JoinColumn(name = "content_id"),
        inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags = new HashSet<>();
}
