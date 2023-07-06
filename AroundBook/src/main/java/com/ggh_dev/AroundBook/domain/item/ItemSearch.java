package com.ggh_dev.AroundBook.domain.item;

import lombok.Data;

/**
 * 상품 검색 조건 파라미터
 */
@Data
public class ItemSearch {
    private SearchCondition condition;
    private String text;
    private ItemSubject subject;
}
