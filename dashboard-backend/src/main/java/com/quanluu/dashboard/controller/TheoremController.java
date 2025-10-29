package com.quanluu.dashboard.controller;

import com.quanluu.dashboard.model.dto.TheoremDTO;
import com.quanluu.dashboard.service.TheoremService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/theorems")
@Tag(name = "Theorems", description = "Theorem management with statement, proof, and field metadata")
public class TheoremController {

    @Autowired
    private TheoremService theoremService;

    @GetMapping
    @Operation(summary = "Get all theorems")
    public List<TheoremDTO> getAllTheorems() {
        return theoremService.getAllTheorems();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get theorem by ID")
    public ResponseEntity<TheoremDTO> getTheoremById(@PathVariable Long id) {
        return theoremService.getTheoremById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Create new theorem")
    public TheoremDTO createTheorem(@RequestBody TheoremDTO theoremDTO) {
        return theoremService.createTheorem(theoremDTO);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update theorem")
    public TheoremDTO updateTheorem(@PathVariable Long id, @RequestBody TheoremDTO theoremDTO) {
        return theoremService.updateTheorem(id, theoremDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete theorem")
    public ResponseEntity<Void> deleteTheorem(@PathVariable Long id) {
        theoremService.deleteTheorem(id);
        return ResponseEntity.noContent().build();
    }
}
