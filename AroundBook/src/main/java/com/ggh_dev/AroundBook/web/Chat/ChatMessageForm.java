package com.ggh_dev.AroundBook.web.Chat;

import lombok.Data;

@Data
public class ChatMessageForm {
    private Long roomId;
    private Long writer;
    private String message;
}
