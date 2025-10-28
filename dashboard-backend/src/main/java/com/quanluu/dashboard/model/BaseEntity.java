package com.quanluu.dashboard.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * Base entity class providing common timestamp fields for all entities.
 * 
 * All entity classes should extend this to automatically get:
 * - createdAt: Timestamp when entity was first created (immutable)
 * - updatedAt: Timestamp when entity was last modified
 * 
 * Timestamps are managed automatically via JPA lifecycle callbacks.
 */
@MappedSuperclass
@Data
public abstract class BaseEntity {
    
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}