package com.quanluu.dashboard.controller;

import com.quanluu.dashboard.model.dto.PaperDTO;
import com.quanluu.dashboard.service.PaperService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/papers")
@Tag(name = "Papers", description = "Paper management with citation and bibliographic metadata")
public class PaperController {

    @Autowired
    private PaperService paperService;

    @GetMapping
    @Operation(summary = "Get all papers")
    public List<PaperDTO> getAllPapers() {
        return paperService.getAllPapers();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get paper by ID")
    public PaperDTO getPaperById(@PathVariable Long id) {
        return paperService.getPaperById(id);
    }

    @PostMapping
    @Operation(summary = "Create new paper")
    public PaperDTO createPaper(@RequestBody PaperDTO paperDTO) {
        return paperService.createPaper(paperDTO);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update paper")
    public PaperDTO updatePaper(@PathVariable Long id, @RequestBody PaperDTO paperDTO) {
        return paperService.updatePaper(id, paperDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete paper")
    public ResponseEntity<Void> deletePaper(@PathVariable Long id) {
        paperService.deletePaper(id);
        return ResponseEntity.noContent().build();
    }
}
