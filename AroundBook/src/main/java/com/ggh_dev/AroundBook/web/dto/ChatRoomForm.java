package com.ggh_dev.AroundBook.web.dto;

import com.ggh_dev.AroundBook.domain.member.Member;
import lombok.Data;

@Data
public class ChatRoomForm {
    private Member seller;
    private Long itemId;
}
