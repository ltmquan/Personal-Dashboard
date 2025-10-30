package com.quanluu.dashboard.service;

import com.quanluu.dashboard.model.dto.TheoremDTO;

import java.util.List;

public interface TheoremService {

    List<TheoremDTO> getAllTheorems();

    TheoremDTO getTheoremById(Long id);

    List<TheoremDTO> searchTheoremsByTitleContaining(String keywords, Integer limit);

    TheoremDTO getMostRecentTheorem();

    TheoremDTO createTheorem(TheoremDTO theoremDTO);

    TheoremDTO updateTheorem(Long id, TheoremDTO theoremDTO);

    void deleteTheorem(Long id);
}
