package com.upd.exmarktask.controllers;

import com.upd.exmarktask.model.dto.DocumentUpdDto;
import com.upd.exmarktask.service.MarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.Objects.nonNull;

@RestController
@RequestMapping("/marks")
@RequiredArgsConstructor
public class MarkController {

    private final MarkService markService;

    @GetMapping
    public ResponseEntity<List<DocumentUpdDto.DetailDto.MarkDto>> getMarks() {
        var marks = markService.showAllMarks();
        return !marks.isEmpty()
                ? new ResponseEntity<>(marks, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DocumentUpdDto.DetailDto.MarkDto> getMark(@PathVariable("id") Long id) {
        var mark = markService.showMarkById(id);
        return nonNull(mark)
                ? new ResponseEntity<>(mark, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteMark(@PathVariable("id") Long id) {
        markService.deleteMarkById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
