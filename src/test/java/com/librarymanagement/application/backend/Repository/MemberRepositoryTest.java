package com.librarymanagement.application.backend.Repository;

import com.librarymanagement.application.backend.dto.Address;
import com.librarymanagement.application.backend.dto.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;


    @Test
    @Rollback(value = false)
    void testSave() {

        Member memberData = new Member("Patty", "Armor","Patty98@gmail.com","129-928-3990");                   // have a member object to be saved
        Address address = new Address("1920 Jackson",
                "Saint Paul","MN","55271",memberData);    // have an address object to be associated
                                                                            // with member object
        memberData.setAddress(address);
        Member member = memberRepository.save(memberData);   // save member and retrieve the payload
        assertNotNull(member);                                 // check that member is not null
        assertTrue(member.getId() > 0);                // check if member have an id.

    }

    @Test
    void testGet() {
        Integer memberID = 2; // provide a member id that is known
        Member member = memberRepository.getOne(memberID);  // retrieve the member for the database
        assertNotNull(member);
        assertTrue(member.getId() == 2);

    }






}