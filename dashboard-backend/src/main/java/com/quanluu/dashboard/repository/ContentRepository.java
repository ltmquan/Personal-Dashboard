package com.quanluu.dashboard.repository;

import com.quanluu.dashboard.constants.ContentType;
import com.quanluu.dashboard.model.Content;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContentRepository extends JpaRepository<Content, Long> {

    List<Content> findByContentType(ContentType contentType);

    @Query("SELECT DISTINCT c FROM Content c JOIN c.tags t WHERE t.name = :tagName")
    List<Content> findByTagName(@Param("tagName") String tagName);

    @Query("SELECT c FROM Content c WHERE LOWER(c.title) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
           "OR LOWER(c.body) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Content> searchByKeyword(@Param("keyword") String keyword);

    @Query("SELECT DISTINCT c FROM Content c JOIN c.tags t WHERE c.contentType = :type AND t.name = :tagName")
    List<Content> findByContentTypeAndTagName(@Param("type") ContentType type, @Param("tagName") String tagName);

    @Query(value = "SELECT * FROM contents WHERE content_type = 'THEOREM' " +
                   "AND (SELECT bool_and(LOWER(title) LIKE LOWER(CONCAT('%', keyword, '%'))) " +
                   "FROM unnest(CAST(:keywords AS text[])) AS keyword) " +
                   "LIMIT :limit", nativeQuery = true)
    List<Content> searchTheoremsByTitleContaining(@Param("keywords") List<String> keywords, @Param("limit") int limit);

    @Query("SELECT c FROM Content c WHERE c.contentType = :type ORDER BY c.createdAt DESC LIMIT 1")
    Optional<Content> getMostRecentByContentType(@Param("type") ContentType type);
}
