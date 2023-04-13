package com.ggh_dev.AroundBook.controller;

import com.ggh_dev.AroundBook.domain.item.Book;
import com.ggh_dev.AroundBook.domain.item.BookCondition;
import com.ggh_dev.AroundBook.domain.item.Item;
import com.ggh_dev.AroundBook.domain.item.SaleStatus;
import com.ggh_dev.AroundBook.domain.member.Member;
import com.ggh_dev.AroundBook.service.ItemService;
import com.ggh_dev.AroundBook.service.MemberService;
import com.ggh_dev.AroundBook.web.argumentresolver.Login;
import com.ggh_dev.AroundBook.web.item.BookForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/items")
public class ItemController {
    private final ItemService itemService;
    private final MemberService memberService;

    /**
     * 책 상품 등록 폼
     */
    @GetMapping("/new")
    public String createBookForm(Model model) {
        model.addAttribute("form", new BookForm());
        return "items/createItemForm";
    }

    /**
     * 책 상품 등록
     */
    @PostMapping("/new")
    public String createBook(@Login Member member, BookForm form) {
        Book book= new Book();
        book.createBook(form);

        //상품을 등록하고자하는 회원 객체
        Member findMember = memberService.findOne(member.getId());
        book.setMember(findMember);

        itemService.saveItem(book);
        return "redirect:/items";
    }

    /**
     * 회원의 상품 목록
     */
    @GetMapping
    public String list(@Login Member member, Model model) {
        //상품을 등록하고자하는 회원 객체
        Member findMember = memberService.findOne(member.getId());

        List<Item> items = itemService.findMemberItems(findMember);
        model.addAttribute("items", items);
        return "items/itemList";
    }

    /**
     * 책 상품 수정 폼
     */
    @GetMapping(value = "/{itemId}/edit")
    public String updateBookForm(@PathVariable("itemId") Long itemId, Model model) {
        Book book = (Book) itemService.findOne(itemId);
        BookForm form= new BookForm();
        form.createBookForm(book);

        model.addAttribute("form", form);
        return "items/updateItemForm";
    }

    /**
     * 책 상품 수정
     */
    @PostMapping(value = "/{itemId}/edit")
    public String updateBook(@PathVariable Long itemId, @ModelAttribute("form") BookForm form) {
        itemService.updateItem(itemId, form);
        return "redirect:/items";
    }

    //--전달 값 매핑--//
    @ModelAttribute("BookCondition")
    public BookCondition[] bookConditionsTypes() {
        return BookCondition.values();
    }

    @ModelAttribute("SaleStatus")
    public SaleStatus[] SaleStatusTypes() {
        return SaleStatus.values();
    }
}
