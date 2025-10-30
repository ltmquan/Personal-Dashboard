package com.quanluu.dashboard.service.impl;

import com.quanluu.dashboard.constants.ContentType;
import com.quanluu.dashboard.constants.EntityType;
import com.quanluu.dashboard.constants.ErrorCode;
import com.quanluu.dashboard.exception.ResourceNotFoundException;
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
		return contentRepository.findByContentType(ContentType.POST).stream()
				.map(this::convertToDTO).collect(Collectors.toList());
	}

	@Override
	public PostDTO getPostById(Long id) {
		return contentRepository.findById(id)
				.filter(c -> c.getContentType() == ContentType.POST)
				.map(this::convertToDTO)
				.orElseThrow(() -> new ResourceNotFoundException(
						"Post not found with id: " + id, EntityType.POST,
						ErrorCode.NOT_FOUND_WITH_ID));
	}

	@Override
	@Transactional
	public PostDTO createPost(PostDTO postDTO) {
		Content content = new Content();
		content.setTitle(postDTO.getTitle());
		content.setBody(postDTO.getBody());
		content.setContentType(ContentType.POST);
		content.setTags(getTagsFromIds(postDTO.getTagIds()));
		content.setReferencedContentIds(getReferencedContentsFromDTO(postDTO));

		content = contentRepository.save(content);

		return convertToDTO(content);
	}

	@Override
	@Transactional
	public PostDTO updatePost(Long id, PostDTO postDTO) {
		Content content = contentRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(
						"Post not found with id: " + id, EntityType.POST,
						ErrorCode.NOT_FOUND_WITH_ID));

		content.setTitle(postDTO.getTitle());
		content.setBody(postDTO.getBody());
		content.setTags(getTagsFromIds(postDTO.getTagIds()));
		content.setReferencedContentIds(getReferencedContentsFromDTO(postDTO));

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
		dto.setCreatedAt(content.getCreatedAt());
		dto.setUpdatedAt(content.getUpdatedAt());
		dto.setTagIds(content.getTags().stream().map(Tag::getId)
				.collect(Collectors.toSet()));

		// Split referenced content IDs by type
		Set<Long> referencedContentIds = content.getReferencedContentIds();
		if (referencedContentIds != null && !referencedContentIds.isEmpty()) {
			splitReferencedIdsByType(referencedContentIds, dto);
		}

		return dto;
	}

	private void splitReferencedIdsByType(Set<Long> ids, PostDTO dto) {
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
								"Tag not found with id: " + id,
								EntityType.TAG, ErrorCode.NOT_FOUND_WITH_ID)))
				.collect(Collectors.toSet());
	}

	private Set<Long> getReferencedContentsFromDTO(PostDTO dto) {
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
