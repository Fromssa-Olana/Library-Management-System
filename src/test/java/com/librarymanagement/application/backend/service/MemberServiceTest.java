package com.librarymanagement.application.backend.service;

import com.librarymanagement.application.backend.Repository.MemberRepository;
import com.librarymanagement.application.backend.dto.Member;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.helger.commons.mock.CommonsAssert.assertEquals;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
class MemberServiceTest {

    @MockBean
    private MemberRepository memberRepository;

    @InjectMocks
    private MemberService memberService;

    /**
     * Testing the get method. It should return the desired member object.
     */
    @Test
    void testGet() {
        Integer memberID = 2;
        Member member = new Member();
        member.setId(2);

        Mockito.when(memberRepository.findById(memberID)).thenReturn(java.util.Optional.of(member));
        Member result = memberService.get(memberID).get();

        assertEquals(result, member);
    }

    /**
     * Testing the save method. It should save the desired member object and return the same object
     */

    @Test
    void testSave() {

        Member member = new Member();
        member.setId(3);

        Mockito.when(memberRepository.save(member)).thenReturn(member);
        Member result = memberService.save(member);

        assertEquals(result, member);


    }

}
