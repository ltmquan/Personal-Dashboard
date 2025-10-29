package com.quanluu.dashboard.repository;

import com.quanluu.dashboard.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

    Optional<Tag> findByNameIgnoreCase(String name);

    boolean existsByNameIgnoreCase(String name);
}
