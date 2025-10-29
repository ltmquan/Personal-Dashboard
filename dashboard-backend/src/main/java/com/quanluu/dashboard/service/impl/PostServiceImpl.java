package com.quanluu.dashboard.service.impl;

import com.quanluu.dashboard.constants.ContentType;
import com.quanluu.dashboard.model.Content;
import com.quanluu.dashboard.model.Tag;
import com.quanluu.dashboard.model.dto.PostDTO;
import com.quanluu.dashboard.repository.ContentRepository;
import com.quanluu.dashboard.repository.TagRepository;
import com.quanluu.dashboard.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private ContentRepository contentRepository;

    @Autowired
    private TagRepository tagRepository;

    @Override
    public List<PostDTO> getAllPosts() {
        return contentRepository.findByContentType(ContentType.POST)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<PostDTO> getPostById(Long id) {
        return contentRepository.findById(id)
                .filter(c -> c.getContentType() == ContentType.POST)
                .map(this::convertToDTO);
    }

    @Override
    @Transactional
    public PostDTO createPost(PostDTO postDTO) {
        Content content = new Content();
        content.setTitle(postDTO.getTitle());
        content.setBody(postDTO.getBody());
        content.setSummary(postDTO.getSummary());
        content.setContentType(ContentType.POST);
        content.setTags(getTagsFromIds(postDTO.getTagIds()));

        content = contentRepository.save(content);

        return convertToDTO(content);
    }

    @Override
    @Transactional
    public PostDTO updatePost(Long id, PostDTO postDTO) {
        Content content = contentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found with id: " + id));

        content.setTitle(postDTO.getTitle());
        content.setBody(postDTO.getBody());
        content.setSummary(postDTO.getSummary());
        content.setTags(getTagsFromIds(postDTO.getTagIds()));

        return convertToDTO(contentRepository.save(content));
    }

    @Override
    @Transactional
    public void deletePost(Long id) {
        contentRepository.deleteById(id);
    }

    private PostDTO convertToDTO(Content content) {
        PostDTO dto = new PostDTO();
        dto.setId(content.getId());
        dto.setTitle(content.getTitle());
        dto.setBody(content.getBody());
        dto.setSummary(content.getSummary());
        dto.setTagIds(content.getTags().stream()
                .map(Tag::getId)
                .collect(Collectors.toSet()));

        return dto;
    }

    private Set<Tag> getTagsFromIds(Set<Long> tagIds) {
        if (tagIds == null || tagIds.isEmpty()) {
            return new HashSet<>();
        }
        return tagIds.stream()
                .map(id -> tagRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Tag not found with id: " + id)))
                .collect(Collectors.toSet());
    }
}
