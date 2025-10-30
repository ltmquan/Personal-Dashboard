package com.quanluu.dashboard.service;

import com.quanluu.dashboard.model.dto.DefinitionDTO;

import java.util.List;

public interface DefinitionService {

    List<DefinitionDTO> getAllDefinitions();

    DefinitionDTO getDefinitionById(Long id);

    DefinitionDTO createDefinition(DefinitionDTO definitionDTO);

    DefinitionDTO updateDefinition(Long id, DefinitionDTO definitionDTO);

    void deleteDefinition(Long id);
}
