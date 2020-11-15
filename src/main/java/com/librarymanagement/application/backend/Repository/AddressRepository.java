package com.librarymanagement.application.backend.Repository;

import com.librarymanagement.application.backend.entity.Address;
import com.librarymanagement.application.backend.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Integer> {
}
