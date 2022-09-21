package com.upd.exmarktask.service;

import com.upd.exmarktask.model.dto.DocumentUpdDto;
import com.upd.exmarktask.repository.DocumentUpdRepository;
import com.upd.exmarktask.repository.MarkUpdRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DocumentService {
    private final DocumentUpdRepository documentUpdRepository;
    private final MarkUpdRepository markUpdRepository;

    public List<DocumentUpdDto> showAllDocuments() {
        return documentUpdRepository.showAll();
    }

    public DocumentUpdDto showDocumentById(Long id) {
        return documentUpdRepository.showById(id);
    }

    public void createDocumentWithMarks(DocumentUpdDto document) {
        var updDocId = documentUpdRepository.createUpdDocument(document);
        document.setId(updDocId);
        for (var detail : document.getDetails()) {
            detail.setDocId(updDocId);
            detail.setId(documentUpdRepository.createUpdDetail(detail));
            var marks = detail.getMarks();
            for (var mark : marks) {
                mark.setMarkId(markUpdRepository.createMark(mark));
                documentUpdRepository.createUpdDocumentRelation(detail.getId(), mark.getMarkId());
            }
        }
    }

    public void deleteDocumentById(Long id) {
        documentUpdRepository.deleteDocumentById(id);
    }
}
