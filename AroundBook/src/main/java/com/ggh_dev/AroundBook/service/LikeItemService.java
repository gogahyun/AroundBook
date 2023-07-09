package com.ggh_dev.AroundBook.service;

import com.ggh_dev.AroundBook.domain.item.Item;
import com.ggh_dev.AroundBook.domain.item.LikeItem;
import com.ggh_dev.AroundBook.domain.member.Member;
import com.ggh_dev.AroundBook.repository.ItemRepository;
import com.ggh_dev.AroundBook.repository.LikeItemRepository;
import com.ggh_dev.AroundBook.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class LikeItemService {
    private final LikeItemRepository likeItemRepository;
    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;

    /**
     * 관심 상품 등록
     */
    public boolean saveLikeItems(Long memberId, Long itemId) {
        //엔티티 조회
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        if (!existLike(member, item)) { //관심 상품 등록
            //관심 상품 생성(추가)
            LikeItem likeItem = new LikeItem();
            likeItem.setItem(item);
            likeItem.setMember(member);

            //관심 상품 저장
            likeItemRepository.save(likeItem);
            return true;
        }else{  //관심 상품 등록 취소
            likeItemRepository.delete(member,item);
            item.removeLikes();

            return false;
        }
    }

    /**
     * 관심 상품 여부 확인
     */
    @Transactional(readOnly = true)
    public boolean existLike(Member member, Item item) {
        return likeItemRepository.exists(member, item);
    }

    /**
     * 관심 상품 리스트 출력 - 회원 엔티티
     * @param member
     * @return
     */
    @Transactional(readOnly = true)
    public List<Item> findLikeItemByMember(Member member) {
        return likeItemRepository.findByMember(member);
    }

}
