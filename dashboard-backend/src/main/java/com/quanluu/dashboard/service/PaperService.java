package com.quanluu.dashboard.service;

import com.quanluu.dashboard.model.dto.PaperDTO;

import java.util.List;
import java.util.Optional;

public interface PaperService {

    List<PaperDTO> getAllPapers();

    Optional<PaperDTO> getPaperById(Long id);

    PaperDTO createPaper(PaperDTO paperDTO);

    PaperDTO updatePaper(Long id, PaperDTO paperDTO);

    void deletePaper(Long id);
}
