package com.librarymanagement.application.data.service;

import com.librarymanagement.application.data.Repository.MemberRepository;
import com.librarymanagement.application.data.entity.AbstractEntity;
import com.librarymanagement.application.data.entity.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemberService {
    private MemberRepository memberRepository;

    public MemberService(@Autowired MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    public void update(Member member) {
        findAll();
    }


    public Optional<Member> get(Integer id) {
        return memberRepository.findById(id);
    }


    public void save(Member member) {
        memberRepository.save(member);
    }
}