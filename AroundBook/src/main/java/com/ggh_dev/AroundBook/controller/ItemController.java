package com.ggh_dev.AroundBook.controller;

import com.ggh_dev.AroundBook.FileStore;
import com.ggh_dev.AroundBook.domain.item.*;
import com.ggh_dev.AroundBook.domain.member.Member;
import com.ggh_dev.AroundBook.service.ItemImageService;
import com.ggh_dev.AroundBook.service.ItemService;
import com.ggh_dev.AroundBook.service.LikeItemService;
import com.ggh_dev.AroundBook.service.MemberService;
import com.ggh_dev.AroundBook.web.argumentresolver.Login;
import com.ggh_dev.AroundBook.web.item.BookForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/items")
public class ItemController {
    private final ItemService itemService;
    private final ItemImageService itemImageService;
    private final MemberService memberService;
    private final LikeItemService likeItemService;

    private final FileStore fileStore;

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
    public String createBook(@Login Member member, BookForm form) throws IOException {
        Book book= new Book();
        
        //상품을 등록하고자하는 회원 객체
        Member findMember = memberService.findOne(member.getId());
        book.setMember(findMember);

        //상품 이미지 객체
        List<ItemImage> images = fileStore.storeFiles(form.getImageFiles()); //상품 이미지 스토리지 저장
        book.createBook(images, form);

        //상품 DB 저장
        itemService.saveItem(book);

        if(!images.isEmpty()){
            itemImageService.saveItemImages(book, images); //상품 이미지 DB 저장
        }

        return "redirect:/items";
    }

    /**
     * 전체 상품 목록
     */
    @GetMapping
    public String list(Model model) {
        List<Item> items = itemService.findItems();
        model.addAttribute("items", items);
        return "items/itemList";
    }

    /**
     * 회원의 상품 목록
     */
    @GetMapping("/members")
    public String list(@Login Member member, Model model) {
        //조회하고자하는 회원 객체
        Member findMember = memberService.findOne(member.getId());

        List<Item> items = itemService.findMemberItems(findMember);
        model.addAttribute("items", items);
        return "items/memberItemList";
    }

    /**
     * 책 상품 수정 폼
     */
    @GetMapping(value = "/edit/{itemId}")
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
    @PostMapping(value = "/edit/{itemId}")
    public String updateBook(@PathVariable Long itemId, @ModelAttribute("form") BookForm form) {
        itemService.updateItem(itemId, form);
        return "redirect:/items/members";
    }

    /**
     * 책 상품 세부 사항
     */
    @GetMapping(value = "/detail/{itemId}")
    public String detailBookForm(@PathVariable("itemId") Long itemId, @Login Member member, Model model) {
        Book book = (Book) itemService.findOne(itemId);

        BookForm form= new BookForm();
        form.createBookForm(book);
        form.setHasLike(likeItemService.existLike(member, book));

        model.addAttribute("form", form);

        //상품 이미지
        List<ItemImage> images = itemImageService.findItemImages(book);
        model.addAttribute("images", images);
        return "items/detailItemForm";
    }

    /**
     * 상품 검색
     */
    @PostMapping("/search")
    public String searchItems(@ModelAttribute("form") ItemSearch itemSearch, Model model) {
        List<Book> items = itemService.searchItems(itemSearch);
        model.addAttribute("items",items);

        return "items/itemSearchList";
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

    @ModelAttribute("SearchCondition")
    public SearchCondition[] SearchConditionTypes() {
        return SearchCondition.values();
    }
}
