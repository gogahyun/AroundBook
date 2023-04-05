package com.ggh_dev.AroundBook.service;

import com.ggh_dev.AroundBook.domain.LikeItem;
import com.ggh_dev.AroundBook.domain.Member;
import com.ggh_dev.AroundBook.domain.item.Item;
import com.ggh_dev.AroundBook.repository.ItemRepository;
import com.ggh_dev.AroundBook.repository.LikeItemRepository;
import com.ggh_dev.AroundBook.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class LikeItemService {
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;
    private final LikeItemRepository likeItemRepository;

    /**
     * 관심 상품 등록
     */
    public Long addLikeItems(Long memberId, Long itemId) {
        //엔티티 조회
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        //관심 상품 생성(추가)
        LikeItem likeItem = LikeItem.addLikeItem(member, item);

        //관심 상품 저장
        likeItemRepository.save(likeItem);
        return likeItem.getId();
    }

    /**
     * 관심 상품 취소
     */
    public void removeLikeItem(Long likeItemId){
        //엔티티 조회
        LikeItem likeItem = likeItemRepository.findOne(likeItemId);
        //상품의 관심 상품 개수 감소
        likeItem.removeLikes();
        //관심 상품 삭제
        likeItemRepository.delete(likeItem);
    }

    /**
     * 관심 상품 리스트 출력 - 회원 엔티티 아이디
     * @param memberId
     * @return
     */
    public List<LikeItem> findLikeItemById(Long memberId) {
        return likeItemRepository.findById(memberId);
    }
}
