package com.librarymanagement.application.data.Repository;

import com.librarymanagement.application.data.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Integer> {
}
