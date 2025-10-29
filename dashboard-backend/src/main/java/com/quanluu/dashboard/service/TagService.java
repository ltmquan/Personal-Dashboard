package com.quanluu.dashboard.service;

import com.quanluu.dashboard.model.Tag;

import java.util.List;
import java.util.Optional;

public interface TagService {

    List<Tag> getAllTags();

    Optional<Tag> getTagById(Long id);

    Optional<Tag> getTagByName(String name);

    Tag createTag(Tag tag);

    Tag updateTag(Long id, Tag tagDetails);

    void deleteTag(Long id);
}
