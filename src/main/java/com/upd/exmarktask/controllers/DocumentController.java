package com.upd.exmarktask.controllers;

import com.upd.exmarktask.model.dto.DocumentUpdDto;
import com.upd.exmarktask.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.Objects.nonNull;

@RestController
@RequestMapping(path = "/document-upd")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService documentService;

    @GetMapping
    public ResponseEntity<List<DocumentUpdDto>> getUpdDocuments() {
        var documents = documentService.showAllDocuments();
        return !documents.isEmpty() ?
                new ResponseEntity<>(documents, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DocumentUpdDto> getUpdDocument(@PathVariable("id") Long id) {
        var document = documentService.showDocumentById(id);
        return nonNull(document) ?
                new ResponseEntity<>(document, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createDocumentAndMarks(@RequestBody DocumentUpdDto document) {
        documentService.createDocumentWithMarks(document);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteDocument(@PathVariable("id") Long id) {
        documentService.deleteDocumentById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
