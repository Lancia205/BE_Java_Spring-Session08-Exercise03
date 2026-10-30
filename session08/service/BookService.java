package com.example.session08.service;

import com.example.session08.dto.BookCreateDTO;
import com.example.session08.dto.BookUpdateStockDTO;
import com.example.session08.entity.Book;
import com.example.session08.exception.ResourceNotFoundException;
import com.example.session08.repository.BookRepository;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    // Thêm mới sách kèm ảnh bìa
    public Book createBook(BookCreateDTO dto) throws IOException {

        MultipartFile file = dto.getCoverImage();

        if (file == null || file.isEmpty()) {
            throw new RuntimeException("Vui lòng chọn ảnh bìa sách!");
        }

        String uploadDir = System.getProperty("user.dir") + "/uploads/";

        File directory = new File(uploadDir);

        if (!directory.exists()) {
            directory.mkdirs();
        }

        String fileName = UUID.randomUUID()
                + "_" + file.getOriginalFilename();

        File destination = new File(directory, fileName);

        file.transferTo(destination);

        Book book = new Book();

        book.setTitle(dto.getTitle());
        book.setAuthor(dto.getAuthor());
        book.setStock(dto.getStock());
        book.setCoverUrl("uploads/" + fileName);

        return bookRepository.save(book);
    }

    // Cập nhật số lượng tồn kho sách
    public Book updateBook(Long id, BookUpdateStockDTO dto) {

        // Tìm sách theo ID
        Book book = bookRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Không tìm thấy sách với ID: " + id
                        )
                );

        // Cập nhật số lượng tồn kho
        book.setStock(dto.getStock());

        // Lưu thay đổi vào Database
        return bookRepository.save(book);
    }
    public Book getBookById(Long id) {

        return bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Không tìm thấy sách với ID: " + id
                ));
    }
}