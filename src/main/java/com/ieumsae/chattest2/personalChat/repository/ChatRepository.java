package com.ieumsae.chattest2.personalChat.repository;

import com.ieumsae.chattest2.personalChat.domain.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat, Long> {

    /**
     * 채팅 idx로 채팅 내역을 조회하고, 전송 시간 순으로 정렬
     *
     * @param chatIdx - 채팅방 식별자
     * @return 정렬된 채팅 내역 리스트
     */
    List<Chat> findByChatIdxOrderByChatSendDtAsc(Long chatIdx);
}