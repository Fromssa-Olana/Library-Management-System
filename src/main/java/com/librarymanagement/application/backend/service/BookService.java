package com.librarymanagement.application.backend.service;

import com.librarymanagement.application.backend.Repository.BookRepository;
import com.librarymanagement.application.backend.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookService {
    private BookRepository bookRepository;

    public BookService(@Autowired BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> findAll() {
        return bookRepository.findAll();
    }


    /**
     *  It search a book object by its id
     * @param id
     *          id of the object
     * @return
     */
    public Optional<Book> get(Integer id) {
        return bookRepository.findById(id);
    }

    /**
     *  It saves an object the is given
     * @param book
     *             void
     */
    public void save(Book book) {
        bookRepository.save(book);
    }

    /**
     *  It filters the list according to the input given
     *
     * @param value
     * @return
     *          filtered list of books
     */
    public List<Book> findAll(String value) {

        List<Book> filtered;
        if (value == null || value.isEmpty()) {
            return findAll();
        } else {
            filtered = findAll().stream()
                    .filter(e -> e.getAuthor().toLowerCase().contains(value.toLowerCase())
                            || e.getTitle().toLowerCase().contains(value.toLowerCase()))
                    .collect(Collectors.toList());

        }
        return filtered;

    }


}
