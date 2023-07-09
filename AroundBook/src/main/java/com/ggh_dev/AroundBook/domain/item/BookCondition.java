package com.ggh_dev.AroundBook.domain.item;

public enum BookCondition {
    GOOD("상"), AVERAGE("중"), BAD("하");

    private final String description;

    BookCondition(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
