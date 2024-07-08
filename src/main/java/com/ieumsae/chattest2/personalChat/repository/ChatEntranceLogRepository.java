package com.ieumsae.chattest2.personalChat.repository;

import com.ieumsae.chattest2.personalChat.domain.ChatEntranceLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatEntranceLogRepository extends JpaRepository<ChatEntranceLog, Long> {
    ChatEntranceLog findByChatIdxAndUserIdx(Long chatIdx, Long userIdx);
    List<ChatEntranceLog> findByChatIdx(Long chatIdx);
}