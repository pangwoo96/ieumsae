package com.ieumsae.chattest2.personalChat.controller;

import com.ieumsae.chattest2.personalChat.domain.Chat;
import com.ieumsae.chattest2.personalChat.service.PersonalChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class PersonalChatController {

    private final SimpMessagingTemplate messagingTemplate;
    private final PersonalChatService personalChatService;

    /**
     * PersonalChatController 생성자
     *
     * @param messagingTemplate - 메시지 전송을 위한 템플릿
     * @param personalChatService - 채팅 관련 서비스
     */
    @Autowired
    public PersonalChatController(SimpMessagingTemplate messagingTemplate, PersonalChatService personalChatService) {
        this.messagingTemplate = messagingTemplate;
        this.personalChatService = personalChatService;
    }

    /**
     * 채팅방 입장 처리
     *
     * @param chatIdx - 채팅방 식별자
     * @param chat - 채팅 정보
     * @param headerAccessor - 세션 정보 접근자
     */
    @MessageMapping("/chat/enter/{chatIdx}")
    public void enterChatRoom(@DestinationVariable Long chatIdx, @Payload Chat chat, SimpMessageHeaderAccessor headerAccessor) {
        String nickname = personalChatService.getUserNickname(chat.getUserIdx());
        headerAccessor.getSessionAttributes().put("username", nickname);
        headerAccessor.getSessionAttributes().put("chatIdx", chatIdx);
        chat.setChatContent(nickname + "님이 입장하셨습니다.");
        messagingTemplate.convertAndSend("/personal/chat/room/" + chatIdx, chat);
    }

    /**
     * 메시지 전송 처리
     *
     * @param chatIdx - 채팅방 식별자
     * @param chat - 전송할 채팅 메시지
     */
    @MessageMapping("/chat/send/{chatIdx}")
    public void sendMessage(@DestinationVariable Long chatIdx, @Payload Chat chat) {
        personalChatService.saveChat(chat);
        messagingTemplate.convertAndSend("/personal/chat/room/" + chatIdx, chat);
    }

    /**
     * 채팅방 페이지 로드
     *
     * @param userInfoIdx - USER_INFO 테이블의 user_idx
     * @param studyGroupIdx - STUDY_GROUP 테이블의 user_idx
     * @return 채팅방 뷰 이름
     */
    @GetMapping("/personal/chat/room/{userInfoIdx}/{studyGroupIdx}")
    public String chatRoom(@PathVariable Long userInfoIdx, @PathVariable Long studyGroupIdx) {
        // TODO: 채팅방 입장 로직 구현
        // 예: 채팅 내역 로드, 사용자 권한 확인 등
        return "chatRoom"; // 채팅방 뷰 이름
    }

    /**
     * 채팅방 URL 생성
     *
     * @param userIdx - 사용자 식별자
     * @return 생성된 채팅방 URL
     */
    @GetMapping("/chat/create/{userIdx}")
    public String createChatRoom(@PathVariable Long userIdx) {
        String chatRoomUrl = personalChatService.createChatRoomUrl(userIdx);
        return "redirect:" + chatRoomUrl;
    }
}