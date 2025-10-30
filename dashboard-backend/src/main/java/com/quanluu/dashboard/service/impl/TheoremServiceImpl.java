package com.quanluu.dashboard.service.impl;

import com.quanluu.dashboard.constants.ContentType;
import com.quanluu.dashboard.constants.EntityType;
import com.quanluu.dashboard.constants.ErrorCode;
import com.quanluu.dashboard.exception.ResourceNotFoundException;
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

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
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
		return contentRepository.findByContentType(ContentType.THEOREM).stream()
				.map(this::convertToDTO).collect(Collectors.toList());
	}

	@Override
	public TheoremDTO getTheoremById(Long id) {
		return contentRepository.findById(id)
				.filter(c -> c.getContentType() == ContentType.THEOREM)
				.map(this::convertToDTO)
				.orElseThrow(() -> new ResourceNotFoundException(
						"Theorem not found with id: " + id, EntityType.THEOREM,
						ErrorCode.NOT_FOUND_WITH_ID));
	}

	@Override
	public List<TheoremDTO> searchTheoremsByTitleContaining(String keywords,
			Integer limit) {
		List<String> keywordList = Arrays.asList(keywords.split(","));
		List<String> trimmedKeywords = keywordList.stream().map(String::trim)
				.collect(Collectors.toList());

		return contentRepository
				.searchTheoremsByTitleContaining(trimmedKeywords, limit)
				.stream().map(this::convertToDTO).collect(Collectors.toList());
	}

	@Override
	public TheoremDTO getMostRecentTheorem() {
		return contentRepository.getMostRecentByContentType(ContentType.THEOREM)
				.map(this::convertToDTO)
				.orElseThrow(() -> new ResourceNotFoundException(
						"No theorems found in database", EntityType.THEOREM,
						ErrorCode.EMPTY_DATABASE));
	}

	@Override
	@Transactional
	public TheoremDTO createTheorem(TheoremDTO theoremDTO) {
		Content content = new Content();
		content.setTitle(theoremDTO.getTitle());
		content.setBody(theoremDTO.getBody());
		content.setContentType(ContentType.THEOREM);
		content.setTags(getTagsFromIds(theoremDTO.getTagIds()));
		content.setReferencedContentIds(getReferencedContentsFromDTO(theoremDTO));

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
				.orElseThrow(() -> new ResourceNotFoundException(
						"Theorem not found with id: " + id, EntityType.THEOREM,
						ErrorCode.NOT_FOUND_WITH_ID));

		content.setTitle(theoremDTO.getTitle());
		content.setBody(theoremDTO.getBody());
		content.setTags(getTagsFromIds(theoremDTO.getTagIds()));
		content.setReferencedContentIds(getReferencedContentsFromDTO(theoremDTO));

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
		theoremMetadataRepository.findByContentId(id).ifPresent(
				metadata -> theoremMetadataRepository.delete(metadata));
		contentRepository.deleteById(id);
	}

	private TheoremDTO convertToDTO(Content content) {
		TheoremDTO dto = new TheoremDTO();
		dto.setId(content.getId());
		dto.setTitle(content.getTitle());
		dto.setBody(content.getBody());
		dto.setCreatedAt(content.getCreatedAt());
		dto.setUpdatedAt(content.getUpdatedAt());
		dto.setTagIds(content.getTags().stream().map(Tag::getId)
				.collect(Collectors.toSet()));

		theoremMetadataRepository.findByContentId(content.getId())
				.ifPresent(metadata -> {
					dto.setStatement(metadata.getStatement());
					dto.setProof(metadata.getProof());
					dto.setField(metadata.getField());
				});

		// Split referenced content IDs by type
		Set<Long> referencedContentIds = content.getReferencedContentIds();
		if (referencedContentIds != null && !referencedContentIds.isEmpty()) {
			splitReferencedIdsByType(referencedContentIds, dto);
		}

		return dto;
	}

	private void splitReferencedIdsByType(Set<Long> ids, TheoremDTO dto) {
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

	private Set<Long> getReferencedContentsFromDTO(TheoremDTO dto) {
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
