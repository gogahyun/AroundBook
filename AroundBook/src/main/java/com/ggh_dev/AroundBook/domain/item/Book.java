package com.ggh_dev.AroundBook.domain.item;

import com.ggh_dev.AroundBook.web.item.BookForm;
import lombok.Getter;

import javax.persistence.*;

@Entity
@DiscriminatorValue("B")
@Getter
public class Book extends Item{
    private String isbn;

    @Enumerated(EnumType.STRING)
    private BookCondition bookCondition;

    private String title;

    private String author;

    private String publisher;

    private String subject;

    public void createBook(BookForm form) {
        this.createItem(form.getPrice(), form.getContent());
        this.isbn=form.getIsbn();
        this.author=form.getAuthor();
        this.bookCondition=form.getBookCondition();
        this.title=form.getTitle();
        this.publisher=form.getTitle();
        this.subject=form.getSubject();
    }
}

