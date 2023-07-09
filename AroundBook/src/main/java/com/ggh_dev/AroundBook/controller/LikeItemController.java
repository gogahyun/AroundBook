package com.ggh_dev.AroundBook.controller;

import com.ggh_dev.AroundBook.domain.item.Item;
import com.ggh_dev.AroundBook.domain.member.Member;
import com.ggh_dev.AroundBook.service.LikeItemService;
import com.ggh_dev.AroundBook.web.argumentresolver.Login;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class LikeItemController {
    private final LikeItemService likeItemService;

    /**
     * 관심 상품 등록
     */
    @GetMapping("/likeItems")
    public String addLikeItems(@RequestParam("id") Long itemId,
                                  @Login Member member) {

        likeItemService.saveLikeItems(member.getId(),itemId);

        return "redirect:/items/detail/"+itemId;
    }

    /**
     * 회원별 관심 상품된 상품 리스트
     */
    @GetMapping("/items/likes")
    public String MemberLikeItems(@Login Member member, Model model) {
        List<Item> likeItems= likeItemService.findLikeItemByMember(member);
        model.addAttribute("items", likeItems);
        return "items/likeItemList";
    }

}
