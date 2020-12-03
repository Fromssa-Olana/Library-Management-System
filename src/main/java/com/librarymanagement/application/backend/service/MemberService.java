package com.librarymanagement.application.backend.service;

import com.librarymanagement.application.backend.Repository.MemberRepository;
import com.librarymanagement.application.backend.dto.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MemberService {
    private MemberRepository memberRepository;

    /**
     * Constructor
     * @param memberRepository
     */
    public MemberService(@Autowired MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /**
     * It returns all of members
     * @return
     *         List of member objects
     *
     */
    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    /**
     * It returns an optional member Object
     * @param id
     *          member id
     * @return
     *      optional member object
     */
    public Optional<Member> get(Integer id) {
        return memberRepository.findById(id);
    }

    /**
     *
     * @param member
     * @return
     *          the saved member object
     */
    public Member save(Member member) {
       return memberRepository.save(member);
    }

    /**
     *  Deletes with member Id
     * @param memberId
     */
    public void delete(int memberId) {
      Optional<Member> member = get(memberId);
      delete(member.get());
    }

    /**
     * Deletes with member object
     * @param member
     *          Member object
     */
    public void delete(Member member) {
        memberRepository.delete(member);
    }

    /**
     *
     * @param value
     *          substring of a first name or last name
     * @return
     *         List of Member objects
     */
    public List<Member> findAll(String value) {

        List<Member> filtered;
        if (value == null || value.isEmpty()) {
            return findAll();
        } else {
            filtered = findAll().stream()
                    .filter(e -> e.getFirstName().toLowerCase().contains(value.toLowerCase())
                            || e.getLastName().toLowerCase().contains(value.toLowerCase()))
                    .collect(Collectors.toList());

        }
        return filtered;

    }


}
