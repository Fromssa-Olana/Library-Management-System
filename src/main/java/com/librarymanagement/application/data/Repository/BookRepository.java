package com.librarymanagement.application.data.Repository;

import com.librarymanagement.application.data.entity.Book;
import com.librarymanagement.application.data.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Integer> {
}
