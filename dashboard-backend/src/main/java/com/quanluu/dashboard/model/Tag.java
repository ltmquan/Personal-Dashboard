package com.quanluu.dashboard.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * Tag entity for categorizing content.
 *
 * Tags are used to organize and filter academic content by topics
 * such as "complexity-theory", "algorithms", "graph-theory", etc.
 *
 * Fields:
 * - name: Unique tag name (e.g., "algorithms")
 * - color: Optional hex color for UI display (e.g., "#4CAF50")
 */
@Entity
@Table(name = "tags")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Tag extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(length = 7)
    private String color;
}
