package com.ggh_dev.AroundBook.service;

import com.ggh_dev.AroundBook.S3ManageFile;
import com.ggh_dev.AroundBook.domain.item.Book;
import com.ggh_dev.AroundBook.domain.item.Item;
import com.ggh_dev.AroundBook.domain.item.ItemImage;
import com.ggh_dev.AroundBook.domain.item.ItemSearch;
import com.ggh_dev.AroundBook.domain.member.Member;
import com.ggh_dev.AroundBook.repository.ItemRepository;
import com.ggh_dev.AroundBook.web.dto.BookForm;
import com.ggh_dev.AroundBook.web.dto.LocationForm;
import com.ggh_dev.AroundBook.web.dto.NaverBookForm;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {
    private final ItemImageService itemImageService;
    private final ItemRepository itemRepository;
    @Autowired
    private ModelMapper modelMapper;
    private final S3ManageFile s3ManageFile;

    /**
     * 상품 등록
     */
    @Transactional
    public void saveItem(BookForm bookForm, Member findMember, NaverBookForm naverBookForm) throws IOException {
        Book book = modelMapper.map(bookForm,Book.class);
        book.setMember(findMember);

        List<ItemImage> images = s3ManageFile.uploadFiles(bookForm.getImageFiles()); //상품 이미지 S3 스토리지 저장

        book.createBook(images,bookForm,naverBookForm);
        itemRepository.save(book);

        if(!images.isEmpty()){
            itemImageService.saveItemImages(book, images); //상품 이미지 DB 저장
        }
    }
    //--상품 조회--//
    /**
     * 상품 전체 조회
     */
    public List<Item> findItems() {
        return itemRepository.findAll();
    }

    /**
     * 주제별 상품 조회
     */
    public List<Item> findItemsBySubject(String sub) {
        return itemRepository.findItemsBySubject(sub);
    }

    /**
     * 상품 단건 조회
     * @param itemId
     */
    public Item findOne(Long itemId) {
        return itemRepository.findOne(itemId);
    }

    /**
     * 상품 최신 5개 조회
     */
    public List<Item> find4Items() {
        return itemRepository.find4Items();
    }

    /**
     * 사용자 지정 지역 별 상품 리스트 조회
     */
    public  List<Item> findItemsByLocation(LocationForm locationForm) {
        return itemRepository.findItemsByLocation(locationForm);
    }

    //--상품 수정--//

    /**
     * 상품 수정
     * @param itemId
     * @param form
     */
    @Transactional
    public void updateItem(Long itemId, BookForm form) {
        Item item = itemRepository.findOne(itemId);
        item.update(form.getPrice(), form.getStatus(),form.getContent());
    }

    //--회원 별 상품 조회--//
    /**
     * 상품 전체 조회
     */
    public List<Item> findMemberItems(Member member) {
        return itemRepository.findMemberAll(member);
    }

    /**
     * 상품 검색
     */
    public List<Book> searchItems(ItemSearch itemSearch) {
        return itemRepository.searchItemsByTitle(itemSearch);
    }

    /**
     * 상품 삭제
     */
    @Transactional
    public void deleteItemById(Long itemId) {
        List<ItemImage> itemImages = itemImageService.deleteByItem(itemRepository.findOne(itemId));//상품 이미지 DB 정보 삭제
        s3ManageFile.deleteFile(itemImages);//S3 스토리지 파일 삭제
        itemRepository.deleteItemById(itemId);//상품 DB 정보 삭제
    }
}
