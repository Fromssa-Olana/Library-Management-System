package com.librarymanagement.application.backend.dto;

import javax.persistence.*;
import java.util.Calendar;

/**
 *  It holds a member and book
 */

@Table
@Entity
public class Hold extends AbstractEntity{

    @OneToOne(fetch = FetchType.EAGER)
    private Book book;
    @ManyToOne(fetch = FetchType.EAGER)
    private Member member;
    private Calendar date;

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }
}
