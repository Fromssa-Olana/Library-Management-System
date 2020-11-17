package com.librarymanagement.application.backend.Repository;

import com.librarymanagement.application.backend.dto.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Integer> {
}
