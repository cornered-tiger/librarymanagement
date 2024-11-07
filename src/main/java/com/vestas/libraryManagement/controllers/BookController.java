package com.vestas.libraryManagement.controllers;

import com.vestas.libraryManagement.dtos.request.BorrowBookRequest;
import com.vestas.libraryManagement.dtos.request.ReturnBookRequest;
import com.vestas.libraryManagement.dtos.response.BookBorrowHistoryDTO;
import com.vestas.libraryManagement.dtos.request.CreateBookRequest;
import com.vestas.libraryManagement.dtos.response.BookDTO;
import com.vestas.libraryManagement.entities.BookBorrowHistory;
import com.vestas.libraryManagement.entities.User;
import com.vestas.libraryManagement.exceptions.BookNotFoundException;
import com.vestas.libraryManagement.facades.LibraryFacade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/books")
@Slf4j
public class BookController {

    @Autowired
    private LibraryFacade libraryFacade;

    @PostMapping
    public ResponseEntity<BookDTO> create(@RequestBody final CreateBookRequest request) {
        final var createdBook = libraryFacade.createBook(request);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{bookId}")
                .buildAndExpand(createdBook.getId()).toUri();
        return ResponseEntity.created(location).body(createdBook);
    }

    @PutMapping("/{bookId}")
    public ResponseEntity<BookDTO> update(@PathVariable final Long bookId, @RequestBody final CreateBookRequest request) throws BookNotFoundException {
        return ResponseEntity.ok().body(libraryFacade.updateBook(bookId, request));
    }

    @GetMapping("")
    public ResponseEntity<List<BookDTO>> getAllBooks() {
        return ResponseEntity.ok().body(libraryFacade.getAllBooks());
    }

    @GetMapping("/available")
    public ResponseEntity<List<BookDTO>> getAvailableBooks() {
        return ResponseEntity.ok().body(libraryFacade.getAvailableBooks());
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable final Long bookId) throws BookNotFoundException {
        return ResponseEntity.ok().body(libraryFacade.getBookById(bookId));
    }

    @DeleteMapping("/{bookId}")
    public ResponseEntity<Void> delete(@PathVariable final Long bookId) throws BookNotFoundException {
        libraryFacade.deleteBook(bookId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{bookId}/borrow")
    public ResponseEntity<Void> borrow(@PathVariable final Long bookId, @RequestBody final BorrowBookRequest request) {
        libraryFacade.borrowBook(bookId, request.getUserId());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{bookId}/return")
    public ResponseEntity<Void> returnBook(@PathVariable final Long bookId, @RequestBody final ReturnBookRequest request) {
        libraryFacade.returnBook(bookId, request.getUserId());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{bookId}/borrow/history")
    public ResponseEntity<List<BookBorrowHistoryDTO>> getBorrowHistory(@PathVariable final Long bookId) {
        return ResponseEntity.ok().body(libraryFacade.getBorrowHistory(bookId));
    }

    @PostMapping("/user")
    public void createUser(@RequestBody final User user) {
        libraryFacade.createUser(user);

    }

}