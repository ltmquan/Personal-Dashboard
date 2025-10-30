package com.quanluu.dashboard.service.impl;

import com.quanluu.dashboard.constants.ContentType;
import com.quanluu.dashboard.constants.EntityType;
import com.quanluu.dashboard.constants.ErrorCode;
import com.quanluu.dashboard.exception.ResourceNotFoundException;
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
		return contentRepository.findByContentType(ContentType.PAPER).stream()
				.map(this::convertToDTO).collect(Collectors.toList());
	}

	@Override
	public PaperDTO getPaperById(Long id) {
		return contentRepository.findById(id)
				.filter(c -> c.getContentType() == ContentType.PAPER)
				.map(this::convertToDTO)
				.orElseThrow(() -> new ResourceNotFoundException(
						"Paper not found with id: " + id, EntityType.PAPER,
						ErrorCode.NOT_FOUND_WITH_ID));
	}

	@Override
	@Transactional
	public PaperDTO createPaper(PaperDTO paperDTO) {
		Content content = new Content();
		content.setTitle(paperDTO.getTitle());
		content.setBody(paperDTO.getBody());
		content.setContentType(ContentType.PAPER);
		content.setTags(getTagsFromIds(paperDTO.getTagIds()));
		content.setReferencedContentIds(getReferencedContentsFromDTO(paperDTO));

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
				.orElseThrow(() -> new ResourceNotFoundException(
						"Paper not found with id: " + id, EntityType.PAPER,
						ErrorCode.NOT_FOUND_WITH_ID));

		content.setTitle(paperDTO.getTitle());
		content.setBody(paperDTO.getBody());
		content.setTags(getTagsFromIds(paperDTO.getTagIds()));
		content.setReferencedContentIds(getReferencedContentsFromDTO(paperDTO));

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
		paperMetadataRepository.findByContentId(id).ifPresent(
				metadata -> paperMetadataRepository.delete(metadata));
		contentRepository.deleteById(id);
	}

	private PaperDTO convertToDTO(Content content) {
		PaperDTO dto = new PaperDTO();
		dto.setId(content.getId());
		dto.setTitle(content.getTitle());
		dto.setBody(content.getBody());
		dto.setCreatedAt(content.getCreatedAt());
		dto.setUpdatedAt(content.getUpdatedAt());
		dto.setTagIds(content.getTags().stream().map(Tag::getId)
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

		// Split referenced content IDs by type
		Set<Long> referencedContentIds = content.getReferencedContentIds();
		if (referencedContentIds != null && !referencedContentIds.isEmpty()) {
			splitReferencedIdsByType(referencedContentIds, dto);
		}

		return dto;
	}

	private void splitReferencedIdsByType(Set<Long> ids, PaperDTO dto) {
		Set<Long> paperIds = new HashSet<>();
		Set<Long> theoremIds = new HashSet<>();
		Set<Long> definitionIds = new HashSet<>();
		Set<Long> postIds = new HashSet<>();

		for (Long id : ids) {
			contentRepository.findById(id).ifPresent(content -> {
				switch (content.getContentType()) {
					case PAPER -> paperIds.add(id);
					case THEOREM -> theoremIds.add(id);
					case DEFINITION -> definitionIds.add(id);
					case POST -> postIds.add(id);
				}
			});
		}

		dto.setReferencedPaperIds(paperIds);
		dto.setReferencedTheoremIds(theoremIds);
		dto.setReferencedDefinitionIds(definitionIds);
		dto.setReferencedPostIds(postIds);
	}

	private Set<Tag> getTagsFromIds(Set<Long> tagIds) {
		if (tagIds == null || tagIds.isEmpty()) {
			return new HashSet<>();
		}
		return tagIds.stream()
				.map(id -> tagRepository.findById(id)
						.orElseThrow(() -> new ResourceNotFoundException(
								"Tag not found with id: " + id, EntityType.TAG,
								ErrorCode.NOT_FOUND_WITH_ID)))
				.collect(Collectors.toSet());
	}

	private Set<Long> getReferencedContentsFromDTO(PaperDTO dto) {
		Set<Long> allReferencedIds = new HashSet<>();
		if (dto.getReferencedPaperIds() != null) {
			allReferencedIds.addAll(dto.getReferencedPaperIds());
		}
		if (dto.getReferencedTheoremIds() != null) {
			allReferencedIds.addAll(dto.getReferencedTheoremIds());
		}
		if (dto.getReferencedDefinitionIds() != null) {
			allReferencedIds.addAll(dto.getReferencedDefinitionIds());
		}
		if (dto.getReferencedPostIds() != null) {
			allReferencedIds.addAll(dto.getReferencedPostIds());
		}

		// Validate that all referenced IDs exist
		for (Long id : allReferencedIds) {
			if (!contentRepository.existsById(id)) {
				throw new ResourceNotFoundException(
						"Content not found with id: " + id,
						EntityType.CONTENT, ErrorCode.NOT_FOUND_WITH_ID);
			}
		}

		return allReferencedIds;
	}
}
