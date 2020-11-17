package com.librarymanagement.application.backend.controller;
import com.librarymanagement.application.backend.dto.Member;
import com.librarymanagement.application.backend.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class MemberController {
   @Autowired
   MemberService memberService;

    //creating a get mapping that retrieves all the members detail from the database
    @GetMapping("/members")
    private List<Member> getAllBooks()
    {
        return memberService.findAll();
    }
    //creating a get mapping that retrieves the detail of a specific member
    @GetMapping("/members/{memberId}")
    private Optional <Member> getBooks(@PathVariable("memberId") int memberId)
    {
        return memberService.get(memberId);
    }
    //creating a delete mapping that deletes a specified member
    @DeleteMapping("/members/{memberId}")
    private void deleteMember(@PathVariable("memberId") int memberId)
    {
        memberService.delete(memberId);
    }
    //creating post mapping that post the member detail in the database
    @PostMapping("/members")
    private Member saveMember(@RequestBody Member member)
    {
        memberService.save(member);
        return member;
    }
    //creating put mapping that updates the member detail
    @PutMapping("/members")
    private Member update(@RequestBody Member member)
    {
        memberService.save(member);
        return member;
    }

}
