package com.librarymanagement.application.backend.service;

import com.librarymanagement.application.backend.Repository.AddressRepository;
import com.librarymanagement.application.backend.Repository.MemberRepository;
import com.librarymanagement.application.backend.entity.Address;
import com.librarymanagement.application.backend.entity.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AddressService {
    private AddressRepository addressRepository;

    public AddressService(@Autowired AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public List<Address> findAll() {
        return addressRepository.findAll();
    }


    public Optional<Address> get(Integer id) {
        return addressRepository.findById(id);
    }


    public void save(Address member) {
        addressRepository.save(member);
    }



    public void delete(Address address) {
        addressRepository.delete(address);
    }
}
