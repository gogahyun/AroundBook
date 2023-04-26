package com.ggh_dev.AroundBook.domain.item;

public enum SearchCondition {
    TITLE("제목") , AUTHOR("작가"), PUBLISHER("출판사");

    private final String description;

    SearchCondition(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
