package com.guardjo.simpleboard.admin.controller;

import com.guardjo.simpleboard.admin.controller.constant.WebSocketConstant;
import com.guardjo.simpleboard.admin.model.ChatMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
@Slf4j
public class ChatBotController {
    private final static String CHAT_BOT_NAME = "Bot";

    @MessageMapping(WebSocketConstant.DEFAULT_BOT_PREFIX)
    @SendTo(WebSocketConstant.BROKER_PREFIX + WebSocketConstant.DEFAULT_CHAT_PREFIX)
    public ChatMessage answerChat(ChatMessage chatMessage) throws InterruptedException {
        Thread.sleep(1000); // 자연스러운 응답 속도를 위해
        String message = String.format("Hello %s, What is %s?", chatMessage.senderName(), chatMessage.message());

        return ChatMessage.of(CHAT_BOT_NAME, message);
    }
}
