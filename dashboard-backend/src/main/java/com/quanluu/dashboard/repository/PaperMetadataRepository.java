package com.quanluu.dashboard.repository;

import com.quanluu.dashboard.model.PaperMetadata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaperMetadataRepository extends JpaRepository<PaperMetadata, Long> {

    Optional<PaperMetadata> findByContentId(Long contentId);
}
