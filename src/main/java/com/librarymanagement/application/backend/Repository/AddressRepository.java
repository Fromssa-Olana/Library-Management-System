package com.librarymanagement.application.backend.Repository;

import com.librarymanagement.application.backend.dto.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Integer> {
}
