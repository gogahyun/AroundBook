package com.ggh_dev.AroundBook.domain.member;

import com.ggh_dev.AroundBook.domain.item.Item;
import com.ggh_dev.AroundBook.domain.item.LikeItem;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Member {
    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    @Column(unique = true)
    private String userId; //로그인을 위한 유저 아이디

    private String password;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id")
    private Location location;

    @OneToMany(mappedBy = "member")
    private List<LikeItem> likeItems=new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Item> items = new ArrayList<>();

    public void setLocation(String zipcode, String address) {
        Location location = new Location();
        location.createAddress(zipcode,address);
        this.location=location;
    }

    public void changeName(String name) {
        this.name=name;
    }
}