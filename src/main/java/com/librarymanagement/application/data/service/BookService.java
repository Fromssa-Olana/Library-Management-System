package com.librarymanagement.application.data.service;

import com.librarymanagement.application.data.Repository.BookRepository;
import com.librarymanagement.application.data.Repository.MemberRepository;
import com.librarymanagement.application.data.entity.AbstractEntity;
import com.librarymanagement.application.data.entity.Book;
import com.librarymanagement.application.data.entity.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    private BookRepository bookRepository;

    public BookService(@Autowired BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> findAll(){
        return bookRepository.findAll();
    }

    public void update(Book book) {
        findAll();
    }


    public Optional<Book> get(Integer id) {
        return bookRepository.findById(id);
    }


    public void save(Book book) {
            bookRepository.save(book);
    }
}
