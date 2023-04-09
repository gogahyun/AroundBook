package com.ggh_dev.AroundBook.controller;

import com.ggh_dev.AroundBook.domain.item.Book;
import com.ggh_dev.AroundBook.domain.item.BookCondition;
import com.ggh_dev.AroundBook.domain.item.Item;
import com.ggh_dev.AroundBook.domain.item.SaleStatus;
import com.ggh_dev.AroundBook.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    /**
     * 책 상품 등록 폼
     */
    @GetMapping("/items/new")
    public String createBookForm(Model model) {
        model.addAttribute("form", new BookForm());
        return "items/createItemForm";
    }

    /**
     * 책 상품 등록
     */
    @PostMapping("/items/new")
    public String createBook(BookForm form) {
        Book book= new Book();
        book.createBook(form);

        itemService.saveItem(book);
        return "redirect:/items";
    }

    /**
     * 상품 목록
     */
    @GetMapping("items")
    public String list(Model model) {
        List<Item> items = itemService.findItems();
        model.addAttribute("items", items);
        return "items/itemList";
    }

    /**
     * 책 상품 수정 폼
     */
    @GetMapping(value = "/items/{itemId}/edit")
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
    @PostMapping(value = "/items/{itemId}/edit")
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
