package com.quanluu.dashboard.service;

import com.quanluu.dashboard.model.dto.TheoremDTO;

import java.util.List;
import java.util.Optional;

public interface TheoremService {

    List<TheoremDTO> getAllTheorems();

    Optional<TheoremDTO> getTheoremById(Long id);

    TheoremDTO createTheorem(TheoremDTO theoremDTO);

    TheoremDTO updateTheorem(Long id, TheoremDTO theoremDTO);

    void deleteTheorem(Long id);
}
