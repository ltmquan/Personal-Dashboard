package com.quanluu.dashboard.controller;

import com.quanluu.dashboard.model.Tag;
import com.quanluu.dashboard.service.TagService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tags")
@io.swagger.v3.oas.annotations.tags.Tag(name = "Tags", description = "Tag management for categorizing content")
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping
    @Operation(summary = "Get all tags")
    public List<Tag> getAllTags() {
        return tagService.getAllTags();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get tag by ID")
    public ResponseEntity<Tag> getTagById(@PathVariable Long id) {
        return tagService.getTagById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/name/{name}")
    @Operation(summary = "Get tag by name")
    public ResponseEntity<Tag> getTagByName(@PathVariable String name) {
        return tagService.getTagByName(name)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Create new tag")
    public Tag createTag(@RequestBody Tag tag) {
        return tagService.createTag(tag);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update tag")
    public Tag updateTag(@PathVariable Long id, @RequestBody Tag tagDetails) {
        return tagService.updateTag(id, tagDetails);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete tag")
    public ResponseEntity<Void> deleteTag(@PathVariable Long id) {
        tagService.deleteTag(id);
        return ResponseEntity.noContent().build();
    }
}
