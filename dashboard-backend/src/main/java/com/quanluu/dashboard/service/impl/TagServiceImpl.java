package com.quanluu.dashboard.service.impl;

import com.quanluu.dashboard.constants.EntityType;
import com.quanluu.dashboard.constants.ErrorCode;
import com.quanluu.dashboard.exception.ResourceNotFoundException;
import com.quanluu.dashboard.model.Tag;
import com.quanluu.dashboard.repository.TagRepository;
import com.quanluu.dashboard.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TagServiceImpl implements TagService {

	@Autowired
	private TagRepository tagRepository;

	@Override
	public List<Tag> getAllTags() {
		return tagRepository.findAll();
	}

	@Override
	public Optional<Tag> getTagById(Long id) {
		return tagRepository.findById(id);
	}

	@Override
	public Optional<Tag> getTagByName(String name) {
		return tagRepository.findByNameIgnoreCase(name);
	}

	@Override
	public Tag createTag(Tag tag) {
		return tagRepository.save(tag);
	}

	@Override
	public Tag updateTag(Long id, Tag tagDetails) {
		Tag tag = tagRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(
						"Tag not found with id: " + id, EntityType.TAG,
						ErrorCode.NOT_FOUND_WITH_ID));

		tag.setName(tagDetails.getName());
		tag.setColor(tagDetails.getColor());

		return tagRepository.save(tag);
	}

	@Override
	public void deleteTag(Long id) {
		tagRepository.deleteById(id);
	}
}
