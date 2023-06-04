package com.guardjo.simpleboard.admin.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public record ChatMessage(
        String senderName,
        String message,
        String sendTime
) {
    public static ChatMessage of(String senderName, String message) {
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("yyyy-MM-dd HH:mm");
        return new ChatMessage(senderName, message, format.format(new Date()));
    }
}
