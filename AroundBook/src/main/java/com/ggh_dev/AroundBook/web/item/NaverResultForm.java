package com.ggh_dev.AroundBook.web.item;

import lombok.*;

import java.util.List;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Data
public class NaverResultForm {
    private String lastBuildDate;
    private int total;
    private int start;
    private int display;
    private List<NaverBookForm> items;
}