package com.librarymanagement.application.backend.entity;

import javax.persistence.*;
import java.util.Calendar;

@Entity
@Table(name = "book")
public class Book extends  AbstractEntity{

    @Column(name = "title")
    private String title;

    @Column(name = "author")
    private String author;

    @Column(name = "due_date")
    private Calendar dueDate;

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
