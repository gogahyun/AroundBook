package com.ggh_dev.AroundBook.domain.item;

import com.ggh_dev.AroundBook.web.item.BookForm;
import com.ggh_dev.AroundBook.web.item.NaverBookForm;
import lombok.Getter;

import javax.persistence.*;
import java.util.List;

@Entity
@DiscriminatorValue("B")
@Getter
public class Book extends Item{
    private String isbn;

    @Enumerated(EnumType.STRING)
    private BookCondition bookCondition;

    private String author;

    private String publisher;

    private String subject;

    public void createBook(List<ItemImage> images, BookForm form, NaverBookForm naverForm) {
        this.createItem(images, form.getPrice(),naverForm.getTitle(), form.getContent());
        this.author=naverForm.getAuthor();
        this.publisher=naverForm.getPublisher();
    }
}

