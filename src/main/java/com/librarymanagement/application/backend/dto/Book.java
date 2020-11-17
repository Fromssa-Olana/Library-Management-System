package com.librarymanagement.application.backend.dto;

import javax.persistence.*;
import java.util.Calendar;

@Entity
@Table()
public class Book extends  AbstractEntity{

    private String title;

    private String author;

    private Calendar dueDate;

    @ManyToOne
    private Member member;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Calendar getDueDate() {
        return dueDate;
    }

    public void setDueDate(Calendar dueDate) {
        this.dueDate = dueDate;
    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", dueDate=" + dueDate +
                '}';
    }
}
