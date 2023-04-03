package com.ggh_dev.AroundBook.domain.item;

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

    private String publisher;

    private String subject;
}
