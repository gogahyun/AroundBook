package com.ggh_dev.AroundBook.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ggh_dev.AroundBook.domain.item.*;
import com.ggh_dev.AroundBook.domain.member.Member;
import com.ggh_dev.AroundBook.service.ItemImageService;
import com.ggh_dev.AroundBook.service.ItemService;
import com.ggh_dev.AroundBook.service.LikeItemService;
import com.ggh_dev.AroundBook.service.MemberService;
import com.ggh_dev.AroundBook.web.argumentresolver.Login;
import com.ggh_dev.AroundBook.web.dto.BookForm;
import com.ggh_dev.AroundBook.web.dto.LocationForm;
import com.ggh_dev.AroundBook.web.dto.NaverBookForm;
import com.ggh_dev.AroundBook.web.dto.NaverResultForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/items")
public class ItemController {
    private final ItemService itemService;
    private final ItemImageService itemImageService;
    private final MemberService memberService;
    private final LikeItemService likeItemService;

    @Value("${naver-client-id}")
    private String clientId;
    @Value("${naver-client-secret}")
    private String clientSecret;

    //--상품 등록--//
    /**
     * 책 상품 등록 폼
     */
    @GetMapping("/new")
    public String createBookForm( Model model) {
        model.addAttribute("bookForm", new BookForm());
        return "items/createItemForm";
    }

    /**
     * 책 상품 등록
     */
    @PostMapping("/new")
    public String createBook(@Login Member member, @Valid @ModelAttribute("bookForm") BookForm bookForm, BindingResult bindingResult) throws IOException {
        if (bindingResult.hasErrors()) {
            return "items/createItemForm";
        }
        Member findMember = memberService.findOne(member.getId());//상품을 등록하고자하는 회원 객체
        NaverBookForm naverBookForm = BookDetailByISBN(bookForm.getIsbn());

        itemService.saveItem(bookForm,findMember,naverBookForm);//상품 DB 저장

        return "forward:/items";
    }

    //--상품 목록 조회--//
    /**
     * 전체 상품 목록
     */
    @RequestMapping
    public String list(@RequestParam(value="sub", required = false,defaultValue = "") String sub, Model model) {
        List<Item> items;
        if(sub=="") {   //전체 상품 목록 조회
            items = itemService.findItems();
        }else{  //주제별 상품 목록 조회
            items = itemService.findItemsBySubject(sub);
        }
        model.addAttribute("items", items);
        return "items/itemList";
    }

    /**
     * 회원의 상품 목록
     */
    @RequestMapping("/members")
    public String list(@Login Member member, Model model) {
        //조회하고자하는 회원 객체
        Member findMember = memberService.findOne(member.getId());

        List<Item> items = itemService.findMemberItems(findMember);
        model.addAttribute("items", items);
        return "items/memberItemList";
    }


    /**
     * 위치별 상품 목록
     */
    @RequestMapping("/loc")
    public String listByLocation(@Login Member member,
                                 @ModelAttribute("loc")LocationForm locationForm,
                                 Model model) {
        List<Item> itemsByLocation = itemService.findItemsByLocation(locationForm);
        model.addAttribute("items", itemsByLocation);
        model.addAttribute("location", locationForm);

        return "items/locationItemList";
    }



    //-- 상품 수정--//
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
    public String updateBook(@PathVariable("itemId") Long itemId, @ModelAttribute("form") BookForm form) {
        itemService.updateItem(itemId, form);
        return "forward:/items/members";
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

    //-- 네이버 검색 API 요청 함수 --//
    public NaverBookForm BookDetailByISBN(String isbn){
        //String apiURL = "https://openapi.naver.com/v1/search/blog?d_isbn=" + isbn;
        URI uri = UriComponentsBuilder
                .fromUriString("https://openapi.naver.com")
                .path("/v1/search/book_adv.json")
                .queryParam("d_isbn", isbn.replace("-",""))
                .encode()
                .build()
                .toUri();

        // Spring 요청 제공 클래스
        RequestEntity<Void> req = RequestEntity
                .get(uri)
                .header("X-Naver-Client-Id", clientId)
                .header("X-Naver-Client-Secret", clientSecret)
                .build();

        //restTemplate
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> resp = restTemplate.exchange(req, String.class);

        // JSON 파싱
        ObjectMapper om = new ObjectMapper();
        NaverResultForm resultVO = null;
        om.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);  // list deserialization 기능 활성화

        try {
            resultVO = om.readValue(resp.getBody(), NaverResultForm.class);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        NaverBookForm books = resultVO.getItems().get(0);

        return books;
    }
}
