package com.quanluu.dashboard.service.impl;

import com.quanluu.dashboard.constants.ContentType;
import com.quanluu.dashboard.model.Content;
import com.quanluu.dashboard.model.DefinitionMetadata;
import com.quanluu.dashboard.model.Tag;
import com.quanluu.dashboard.model.dto.DefinitionDTO;
import com.quanluu.dashboard.repository.ContentRepository;
import com.quanluu.dashboard.repository.DefinitionMetadataRepository;
import com.quanluu.dashboard.repository.TagRepository;
import com.quanluu.dashboard.service.DefinitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DefinitionServiceImpl implements DefinitionService {

    @Autowired
    private ContentRepository contentRepository;

    @Autowired
    private DefinitionMetadataRepository definitionMetadataRepository;

    @Autowired
    private TagRepository tagRepository;

    @Override
    public List<DefinitionDTO> getAllDefinitions() {
        return contentRepository.findByContentType(ContentType.DEFINITION)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<DefinitionDTO> getDefinitionById(Long id) {
        return contentRepository.findById(id)
                .filter(c -> c.getContentType() == ContentType.DEFINITION)
                .map(this::convertToDTO);
    }

    @Override
    @Transactional
    public DefinitionDTO createDefinition(DefinitionDTO definitionDTO) {
        Content content = new Content();
        content.setTitle(definitionDTO.getTitle());
        content.setBody(definitionDTO.getBody());
        content.setSummary(definitionDTO.getSummary());
        content.setContentType(ContentType.DEFINITION);
        content.setTags(getTagsFromIds(definitionDTO.getTagIds()));

        content = contentRepository.save(content);

        DefinitionMetadata metadata = new DefinitionMetadata();
        metadata.setContent(content);
        metadata.setTerm(definitionDTO.getTerm());
        metadata.setField(definitionDTO.getField());
        metadata.setFormalDefinition(definitionDTO.getFormalDefinition());
        metadata.setRelatedConcepts(definitionDTO.getRelatedConcepts());

        definitionMetadataRepository.save(metadata);

        return convertToDTO(content);
    }

    @Override
    @Transactional
    public DefinitionDTO updateDefinition(Long id, DefinitionDTO definitionDTO) {
        Content content = contentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Definition not found with id: " + id));

        content.setTitle(definitionDTO.getTitle());
        content.setBody(definitionDTO.getBody());
        content.setSummary(definitionDTO.getSummary());
        content.setTags(getTagsFromIds(definitionDTO.getTagIds()));

        content = contentRepository.save(content);

        DefinitionMetadata metadata = definitionMetadataRepository.findByContentId(id)
                .orElse(new DefinitionMetadata());

        metadata.setContent(content);
        metadata.setTerm(definitionDTO.getTerm());
        metadata.setField(definitionDTO.getField());
        metadata.setFormalDefinition(definitionDTO.getFormalDefinition());
        metadata.setRelatedConcepts(definitionDTO.getRelatedConcepts());

        definitionMetadataRepository.save(metadata);

        return convertToDTO(content);
    }

    @Override
    @Transactional
    public void deleteDefinition(Long id) {
        definitionMetadataRepository.findByContentId(id)
                .ifPresent(metadata -> definitionMetadataRepository.delete(metadata));
        contentRepository.deleteById(id);
    }

    private DefinitionDTO convertToDTO(Content content) {
        DefinitionDTO dto = new DefinitionDTO();
        dto.setId(content.getId());
        dto.setTitle(content.getTitle());
        dto.setBody(content.getBody());
        dto.setSummary(content.getSummary());
        dto.setTagIds(content.getTags().stream()
                .map(Tag::getId)
                .collect(Collectors.toSet()));

        definitionMetadataRepository.findByContentId(content.getId())
                .ifPresent(metadata -> {
                    dto.setTerm(metadata.getTerm());
                    dto.setField(metadata.getField());
                    dto.setFormalDefinition(metadata.getFormalDefinition());
                    dto.setRelatedConcepts(metadata.getRelatedConcepts());
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
