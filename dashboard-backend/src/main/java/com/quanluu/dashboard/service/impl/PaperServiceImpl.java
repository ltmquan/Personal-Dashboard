package com.quanluu.dashboard.service.impl;

import com.quanluu.dashboard.constants.ContentType;
import com.quanluu.dashboard.model.Content;
import com.quanluu.dashboard.model.PaperMetadata;
import com.quanluu.dashboard.model.Tag;
import com.quanluu.dashboard.model.dto.PaperDTO;
import com.quanluu.dashboard.repository.ContentRepository;
import com.quanluu.dashboard.repository.PaperMetadataRepository;
import com.quanluu.dashboard.repository.TagRepository;
import com.quanluu.dashboard.service.PaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PaperServiceImpl implements PaperService {

    @Autowired
    private ContentRepository contentRepository;

    @Autowired
    private PaperMetadataRepository paperMetadataRepository;

    @Autowired
    private TagRepository tagRepository;

    @Override
    public List<PaperDTO> getAllPapers() {
        return contentRepository.findByContentType(ContentType.PAPER)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<PaperDTO> getPaperById(Long id) {
        return contentRepository.findById(id)
                .filter(c -> c.getContentType() == ContentType.PAPER)
                .map(this::convertToDTO);
    }

    @Override
    @Transactional
    public PaperDTO createPaper(PaperDTO paperDTO) {
        Content content = new Content();
        content.setTitle(paperDTO.getTitle());
        content.setBody(paperDTO.getBody());
        content.setSummary(paperDTO.getSummary());
        content.setContentType(ContentType.PAPER);
        content.setTags(getTagsFromIds(paperDTO.getTagIds()));

        content = contentRepository.save(content);

        PaperMetadata metadata = new PaperMetadata();
        metadata.setContent(content);
        metadata.setAuthors(paperDTO.getAuthors());
        metadata.setVenue(paperDTO.getVenue());
        metadata.setYear(paperDTO.getYear());
        metadata.setDoi(paperDTO.getDoi());
        metadata.setCitationKey(paperDTO.getCitationKey());
        metadata.setPdfUrl(paperDTO.getPdfUrl());
        metadata.setMyNotes(paperDTO.getMyNotes());

        paperMetadataRepository.save(metadata);

        return convertToDTO(content);
    }

    @Override
    @Transactional
    public PaperDTO updatePaper(Long id, PaperDTO paperDTO) {
        Content content = contentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Paper not found with id: " + id));

        content.setTitle(paperDTO.getTitle());
        content.setBody(paperDTO.getBody());
        content.setSummary(paperDTO.getSummary());
        content.setTags(getTagsFromIds(paperDTO.getTagIds()));

        content = contentRepository.save(content);

        PaperMetadata metadata = paperMetadataRepository.findByContentId(id)
                .orElse(new PaperMetadata());

        metadata.setContent(content);
        metadata.setAuthors(paperDTO.getAuthors());
        metadata.setVenue(paperDTO.getVenue());
        metadata.setYear(paperDTO.getYear());
        metadata.setDoi(paperDTO.getDoi());
        metadata.setCitationKey(paperDTO.getCitationKey());
        metadata.setPdfUrl(paperDTO.getPdfUrl());
        metadata.setMyNotes(paperDTO.getMyNotes());

        paperMetadataRepository.save(metadata);

        return convertToDTO(content);
    }

    @Override
    @Transactional
    public void deletePaper(Long id) {
        paperMetadataRepository.findByContentId(id)
                .ifPresent(metadata -> paperMetadataRepository.delete(metadata));
        contentRepository.deleteById(id);
    }

    private PaperDTO convertToDTO(Content content) {
        PaperDTO dto = new PaperDTO();
        dto.setId(content.getId());
        dto.setTitle(content.getTitle());
        dto.setBody(content.getBody());
        dto.setSummary(content.getSummary());
        dto.setTagIds(content.getTags().stream()
                .map(Tag::getId)
                .collect(Collectors.toSet()));

        paperMetadataRepository.findByContentId(content.getId())
                .ifPresent(metadata -> {
                    dto.setAuthors(metadata.getAuthors());
                    dto.setVenue(metadata.getVenue());
                    dto.setYear(metadata.getYear());
                    dto.setDoi(metadata.getDoi());
                    dto.setCitationKey(metadata.getCitationKey());
                    dto.setPdfUrl(metadata.getPdfUrl());
                    dto.setMyNotes(metadata.getMyNotes());
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
