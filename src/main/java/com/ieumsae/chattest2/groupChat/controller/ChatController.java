package com.ieumsae.chattest2.groupChat.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;

@Controller
public class ChatController {

    @GetMapping("/chat/{studyIdx}")
    public String chatRoom(@PathVariable Integer studyIdx, Model model, Principal principal) {
        // 실제 구현에서는 Principal에서 사용자 정보를 가져와야 합니다.
        Integer currentUserIdx = 1; // 임시 값, 실제로는 로그인한 사용자의 ID를 사용해야 합니다.

        model.addAttribute("studyIdx", studyIdx);
        model.addAttribute("currentUserIdx", currentUserIdx);
        return "chat";
    }
}