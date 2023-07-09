package com.ggh_dev.AroundBook.domain.item;

public enum SaleStatus {
    SALE("판매 중") , RESERVATION("예약 중"), SOLDOUT("판매 완료");

    private final String description;

    SaleStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
