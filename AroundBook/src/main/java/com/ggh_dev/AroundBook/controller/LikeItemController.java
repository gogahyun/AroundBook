package com.ggh_dev.AroundBook.controller;

import com.ggh_dev.AroundBook.domain.member.Member;
import com.ggh_dev.AroundBook.service.LikeItemService;
import com.ggh_dev.AroundBook.web.argumentresolver.Login;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

}
