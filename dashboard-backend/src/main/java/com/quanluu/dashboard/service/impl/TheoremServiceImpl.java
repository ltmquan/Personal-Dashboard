package com.quanluu.dashboard.service.impl;

import com.quanluu.dashboard.constants.ContentType;
import com.quanluu.dashboard.model.Content;
import com.quanluu.dashboard.model.Tag;
import com.quanluu.dashboard.model.TheoremMetadata;
import com.quanluu.dashboard.model.dto.TheoremDTO;
import com.quanluu.dashboard.repository.ContentRepository;
import com.quanluu.dashboard.repository.TagRepository;
import com.quanluu.dashboard.repository.TheoremMetadataRepository;
import com.quanluu.dashboard.service.TheoremService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TheoremServiceImpl implements TheoremService {

    @Autowired
    private ContentRepository contentRepository;

    @Autowired
    private TheoremMetadataRepository theoremMetadataRepository;

    @Autowired
    private TagRepository tagRepository;

    @Override
    public List<TheoremDTO> getAllTheorems() {
        return contentRepository.findByContentType(ContentType.THEOREM)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<TheoremDTO> getTheoremById(Long id) {
        return contentRepository.findById(id)
                .filter(c -> c.getContentType() == ContentType.THEOREM)
                .map(this::convertToDTO);
    }

    @Override
    @Transactional
    public TheoremDTO createTheorem(TheoremDTO theoremDTO) {
        Content content = new Content();
        content.setTitle(theoremDTO.getTitle());
        content.setBody(theoremDTO.getBody());
        content.setSummary(theoremDTO.getSummary());
        content.setContentType(ContentType.THEOREM);
        content.setTags(getTagsFromIds(theoremDTO.getTagIds()));

        content = contentRepository.save(content);

        TheoremMetadata metadata = new TheoremMetadata();
        metadata.setContent(content);
        metadata.setStatement(theoremDTO.getStatement());
        metadata.setProof(theoremDTO.getProof());
        metadata.setField(theoremDTO.getField());

        theoremMetadataRepository.save(metadata);

        return convertToDTO(content);
    }

    @Override
    @Transactional
    public TheoremDTO updateTheorem(Long id, TheoremDTO theoremDTO) {
        Content content = contentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Theorem not found with id: " + id));

        content.setTitle(theoremDTO.getTitle());
        content.setBody(theoremDTO.getBody());
        content.setSummary(theoremDTO.getSummary());
        content.setTags(getTagsFromIds(theoremDTO.getTagIds()));

        content = contentRepository.save(content);

        TheoremMetadata metadata = theoremMetadataRepository.findByContentId(id)
                .orElse(new TheoremMetadata());

        metadata.setContent(content);
        metadata.setStatement(theoremDTO.getStatement());
        metadata.setProof(theoremDTO.getProof());
        metadata.setField(theoremDTO.getField());

        theoremMetadataRepository.save(metadata);

        return convertToDTO(content);
    }

    @Override
    @Transactional
    public void deleteTheorem(Long id) {
        theoremMetadataRepository.findByContentId(id)
                .ifPresent(metadata -> theoremMetadataRepository.delete(metadata));
        contentRepository.deleteById(id);
    }

    private TheoremDTO convertToDTO(Content content) {
        TheoremDTO dto = new TheoremDTO();
        dto.setId(content.getId());
        dto.setTitle(content.getTitle());
        dto.setBody(content.getBody());
        dto.setSummary(content.getSummary());
        dto.setTagIds(content.getTags().stream()
                .map(Tag::getId)
                .collect(Collectors.toSet()));

        theoremMetadataRepository.findByContentId(content.getId())
                .ifPresent(metadata -> {
                    dto.setStatement(metadata.getStatement());
                    dto.setProof(metadata.getProof());
                    dto.setField(metadata.getField());
                });

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
