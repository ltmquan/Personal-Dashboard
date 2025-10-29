package com.quanluu.dashboard.repository;

import com.quanluu.dashboard.model.DefinitionMetadata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DefinitionMetadataRepository extends JpaRepository<DefinitionMetadata, Long> {

    Optional<DefinitionMetadata> findByContentId(Long contentId);
}
