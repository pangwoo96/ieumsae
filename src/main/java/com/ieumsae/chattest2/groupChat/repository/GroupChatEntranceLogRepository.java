package com.ieumsae.chattest2.groupChat.repository;

import com.ieumsae.chattest2.groupChat.domain.GroupChatEntranceLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupChatEntranceLogRepository extends JpaRepository<GroupChatEntranceLog, Long> {
    /**
     * 채팅방 입장 로그를 저장하고 조회하는 기본적인 CRUD 작업을 수행합니다.
     * 필요한 경우 추가적인 쿼리 메서드를 여기에 정의할 수 있습니다.
     */
}