package com.ieumsae.chattest2.groupChat.controller;

import com.ieumsae.chattest2.groupChat.domain.GroupChat;
import com.ieumsae.chattest2.groupChat.domain.UserInfo;
import com.ieumsae.chattest2.groupChat.service.GroupChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;

@Controller
public class GroupChatController {

    private final GroupChatService chatService;

    /**
     * GroupChatController 생성자
     * @param chatService 그룹 채팅 서비스
     */
    @Autowired
    public GroupChatController(GroupChatService chatService) {
        this.chatService = chatService;
    }

    /**
     * 채팅방 입장 처리
     * @param groupChat 그룹 채팅 정보
     */
    @MessageMapping("/chat/enterRoom")
    public void enterRoom(@Payload GroupChat groupChat) {
        chatService.processRoomEntry(groupChat);
    }

    /**
     * 메시지 전송 처리
     * @param groupChat 그룹 채팅 정보
     */
    @MessageMapping("/chat/sendMessage")
    public void sendMessage(@Payload GroupChat groupChat) {
        chatService.sendMessage(groupChat);
    }

    /**
     * 채팅방 페이지 로드
     * @param studyIdx 스터디 인덱스
     * @param model 모델 객체
     * @param principal 현재 인증된 사용자 정보
     * @return 채팅방 뷰 이름
     */
    @GetMapping("/chat/{studyIdx}")
    public String chatRoom(@PathVariable Integer studyIdx, Model model, Principal principal) {
        UserInfo userInfo = chatService.getUserInfo(principal.getName());
        model.addAttribute("studyIdx", studyIdx);
        model.addAttribute("currentUserIdx", userInfo.getUserIdx());
        model.addAttribute("currentUserNickname", userInfo.getUserNickName());
        return "chat";
    }
}