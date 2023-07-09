package com.ggh_dev.AroundBook.domain.item;

public enum ItemSubject {
    총류("0") , 철학("1"), 종교("2"), 사회과학("3"), 자연과학("4"),
    기술과학("5"), 예술("6"), 언어("7"), 문학("8"), 역사("9");

    private final String num;

    ItemSubject(String num) {
        this.num = num;
    }

    public String getNum() {
        return num;
    }
}
