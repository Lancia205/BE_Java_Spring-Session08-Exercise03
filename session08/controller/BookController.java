package com.example.session08.controller;

import com.example.session08.dto.BookCreateDTO;
import com.example.session08.dto.BookUpdateStockDTO;
import com.example.session08.entity.Book;
import com.example.session08.service.BookService;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    // API thêm mới sách
    @PostMapping
    public ResponseEntity<Book> createBook(
            @ModelAttribute BookCreateDTO dto) throws IOException {

        Book book = bookService.createBook(dto);

        return ResponseEntity.ok(book);
    }

    // API cập nhật số lượng tồn kho
    @PatchMapping("/update/{id}")
    public ResponseEntity<Book> updateBook(
            @PathVariable Long id,
            @Valid @RequestBody BookUpdateStockDTO dto) {

        Book book = bookService.updateBook(id, dto);

        return ResponseEntity.ok(book);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(
            @PathVariable Long id) {

        Book book = bookService.getBookById(id);

        return ResponseEntity.ok(book);
    }
}