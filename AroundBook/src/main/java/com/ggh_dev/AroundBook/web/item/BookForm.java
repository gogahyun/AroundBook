package com.ggh_dev.AroundBook.web.item;

import com.ggh_dev.AroundBook.domain.item.Book;
import com.ggh_dev.AroundBook.domain.item.BookCondition;
import com.ggh_dev.AroundBook.domain.item.SaleStatus;
import com.ggh_dev.AroundBook.domain.member.Member;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class BookForm {
    private Long id;

    private Member seller;

    private int price;
    private String content;
    private SaleStatus status; //판매 상태 [SALE, RESERVATION, SOLDOUT]
    private int likes; //관심 상품 개수

    private String isbn;
    private String author;
    private BookCondition bookCondition;
    private String title;
    private String publisher;
    private String subject;

    private boolean hasLike;

    private List<MultipartFile> imageFiles;

    public void createBookForm(Book book) {
        this.id =book.getId();

        this.seller = book.getMember();

        this.price=book.getPrice();
        this.content=book.getContent();
        this.status=book.getStatus();
        this.likes=book.getLikes();

        this.isbn=book.getIsbn();
        this.author= book.getAuthor();
        this.bookCondition=book.getBookCondition();
        this.title= book.getTitle();
        this.publisher=book.getPublisher();
        this.subject= book.getSubject();
    }
}
