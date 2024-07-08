package com.ieumsae.chattest2.groupChat.repository;

import com.ieumsae.chattest2.groupChat.domain.GroupChat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupChatRepository extends JpaRepository<GroupChat, Long> {
    /**
     * 스터디 인덱스로 채팅 메시지를 조회하고 전송 시간순으로 정렬
     * @param studyIdx 스터디 인덱스
     * @return 정렬된 채팅 메시지 리스트
     */
    List<GroupChat> findByStudyIdxOrderByChatSendDtAsc(int studyIdx);
}