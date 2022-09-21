package com.upd.exmarktask.service;


import com.upd.exmarktask.model.dto.DocumentUpdDto;
import com.upd.exmarktask.repository.MarkUpdRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MarkService {
    private final MarkUpdRepository markUpdRepository;

    public List<DocumentUpdDto.DetailDto.MarkDto> showAllMarks() {
        return markUpdRepository.showAll();
    }

    public DocumentUpdDto.DetailDto.MarkDto showMarkById(Long id) {
        return markUpdRepository.showById(id);
    }

    public void deleteMarkById(Long id) {
        markUpdRepository.deleteById(id);
    }
}
