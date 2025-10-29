package com.quanluu.dashboard.repository;

import com.quanluu.dashboard.model.TheoremMetadata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TheoremMetadataRepository extends JpaRepository<TheoremMetadata, Long> {

    Optional<TheoremMetadata> findByContentId(Long contentId);
}
