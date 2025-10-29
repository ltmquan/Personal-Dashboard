package com.quanluu.dashboard.controller;

import com.quanluu.dashboard.model.dto.DefinitionDTO;
import com.quanluu.dashboard.service.DefinitionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/definitions")
@Tag(name = "Definitions", description = "Definition management with term, field, and formal definition metadata")
public class DefinitionController {

    @Autowired
    private DefinitionService definitionService;

    @GetMapping
    @Operation(summary = "Get all definitions")
    public List<DefinitionDTO> getAllDefinitions() {
        return definitionService.getAllDefinitions();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get definition by ID")
    public ResponseEntity<DefinitionDTO> getDefinitionById(@PathVariable Long id) {
        return definitionService.getDefinitionById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Create new definition")
    public DefinitionDTO createDefinition(@RequestBody DefinitionDTO definitionDTO) {
        return definitionService.createDefinition(definitionDTO);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update definition")
    public DefinitionDTO updateDefinition(@PathVariable Long id, @RequestBody DefinitionDTO definitionDTO) {
        return definitionService.updateDefinition(id, definitionDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete definition")
    public ResponseEntity<Void> deleteDefinition(@PathVariable Long id) {
        definitionService.deleteDefinition(id);
        return ResponseEntity.noContent().build();
    }
}
