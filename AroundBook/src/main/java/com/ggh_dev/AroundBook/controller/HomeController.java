package com.ggh_dev.AroundBook.controller;

import com.ggh_dev.AroundBook.domain.item.Item;
import com.ggh_dev.AroundBook.domain.item.ItemImage;
import com.ggh_dev.AroundBook.domain.item.ItemSearch;
import com.ggh_dev.AroundBook.domain.item.SearchCondition;
import com.ggh_dev.AroundBook.domain.member.Member;
import com.ggh_dev.AroundBook.service.ItemImageService;
import com.ggh_dev.AroundBook.service.ItemService;
import com.ggh_dev.AroundBook.web.argumentresolver.Login;
import com.ggh_dev.AroundBook.web.dto.ItemAddressForm;
import com.ggh_dev.AroundBook.web.dto.LocationForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final ItemService itemService;
    private final ItemImageService itemImageService;


    @RequestMapping("/")
    public String home(@Login Member member, Model model) {
        //세션에 회원 데이터가 있는 경우
        if (member != null) {
            model.addAttribute("member",member);
        }
        //주소별 상품
        model.addAttribute("address", new ItemAddressForm());

        //전체 상품 리스트의 이미지
        List<Item> items = itemService.find4Items();
        List<ItemImage> images = itemImageService.findItemListImages(items);
        model.addAttribute("images", images);

        //상품 검색 폼
        model.addAttribute("form",new ItemSearch());

        model.addAttribute("loc", new LocationForm());
        return "home";
    }


    @ModelAttribute("SearchCondition")
    public SearchCondition[] SearchConditionTypes() {
        return SearchCondition.values();
    }
}
