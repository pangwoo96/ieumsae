package com.ieumsae.chattest2.personalChat.service;

import com.ieumsae.chattest2.personalChat.domain.Chat;
import com.ieumsae.chattest2.personalChat.domain.ChatEntranceLog;
import com.ieumsae.chattest2.personalChat.domain.StudyGroup;
import com.ieumsae.chattest2.personalChat.domain.UserInfo;
import com.ieumsae.chattest2.personalChat.repository.ChatEntranceLogRepository;
import com.ieumsae.chattest2.personalChat.repository.ChatRepository;
import com.ieumsae.chattest2.personalChat.repository.StudyGroupRepository;
import com.ieumsae.chattest2.personalChat.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonalChatService {

    private final ChatRepository chatRepository;
    private final UserInfoRepository userInfoRepository;
    private final StudyGroupRepository studyGroupRepository;
    private final ChatEntranceLogRepository chatEntranceLogRepository;

    /**
     * PersonalChatService 생성자
     *
     * @param chatRepository       - 채팅 저장소
     * @param userInfoRepository   - 사용자 정보 저장소
     * @param studyGroupRepository - 스터디 그룹 저장소
     */
    @Autowired
    public PersonalChatService(ChatRepository chatRepository, UserInfoRepository userInfoRepository, StudyGroupRepository studyGroupRepository, ChatEntranceLogRepository chatEntranceLogRepository) {
        this.chatRepository = chatRepository;
        this.userInfoRepository = userInfoRepository;
        this.studyGroupRepository = studyGroupRepository;
        this.chatEntranceLogRepository = chatEntranceLogRepository;
    }

    /**
     * 채팅 메시지 저장
     *
     * @param chat - 저장할 채팅 메시지
     */
    public void saveChat(Chat chat) {
        chatRepository.save(chat);
    }

    /**
     * 채팅 내역 조회
     *
     * @param chatIdx - 채팅방 식별자
     * @return 채팅 내역 리스트
     */
    public List<Chat> getChatHistory(Long chatIdx) {
        return chatRepository.findByChatIdxOrderByChatSendDtAsc(chatIdx);
    }

    /**
     * 사용자 닉네임 조회
     *
     * @param userIdx - 사용자 식별자
     * @return 사용자 닉네임 (없을 경우 "알 수 없음")
     */
    public String getUserNickname(Long userIdx) {
        UserInfo userInfo = userInfoRepository.findByUserIdx(userIdx);
        return userInfo != null ? userInfo.getUserNickName() : "알 수 없음";
    }

    /**
     * 채팅방 생성
     *
     * @param senderIdx   - 발신자 식별자
     * @param receiverIdx - 수신자 식별자
     * @return 생성된 채팅방 식별자
     */
    public Long createChatRoom(Long senderIdx, Long receiverIdx) {
        // 수신자(receiverIdx)가 스터디 방장인지 확인
        StudyGroup studyGroup = studyGroupRepository.findByUserIdx(receiverIdx);
        if (studyGroup == null) {
            throw new IllegalArgumentException("수신자는 스터디 방장이어야 합니다.");
        }

        // chatIdx 생성
        Long chatIdx = Long.parseLong("1" + String.format("%04d", senderIdx) + String.format("%04d", receiverIdx));

        // ChatEntranceLog 생성 및 저장
        ChatEntranceLog senderLog = new ChatEntranceLog();
        senderLog.setChatIdx(chatIdx);
        senderLog.setUserIdx(senderIdx);
        chatEntranceLogRepository.save(senderLog);

        ChatEntranceLog receiverLog = new ChatEntranceLog();
        receiverLog.setChatIdx(chatIdx);
        receiverLog.setUserIdx(receiverIdx);
        chatEntranceLogRepository.save(receiverLog);

        return chatIdx;
    }

    /**
     * 채팅방 입장 가능 여부 확인
     *
     * @param userIdx - 사용자 식별자
     * @param chatIdx - 채팅방 식별자
     * @return 입장 가능 여부
     */
    public boolean canEnterChatRoom(Long userIdx, Long chatIdx) {
        // 채팅방 입장 로그 확인
        ChatEntranceLog log = chatEntranceLogRepository.findByChatIdxAndUserIdx(chatIdx, userIdx);
        if (log == null) {
            return false; // 해당 사용자의 입장 기록이 없으면 입장 불가
        }

        // USER_INFO 확인
        UserInfo userInfo = userInfoRepository.findByUserIdx(userIdx);
        if (userInfo == null) {
            return false; // 사용자 정보가 없으면 입장 불가
        }

        // 채팅 참여자 중 한 명이 스터디 방장인지 확인
        String chatIdxStr = String.valueOf(chatIdx);
        Long senderIdx = Long.parseLong(chatIdxStr.substring(1, 5));
        Long receiverIdx = Long.parseLong(chatIdxStr.substring(5, 9));

        StudyGroup studyGroup = studyGroupRepository.findByUserIdx(receiverIdx);
        if (studyGroup == null) {
            return false; // 수신자가 스터디 방장이 아니면 입장 불가
        }

        return true; // 모든 조건을 만족하면 입장 가능
    }

    /**
     * 채팅방 URL 생성
     *
     * @param userIdx - 사용자 식별자
     * @return 생성된 채팅방 URL
     */
    public String createChatRoomUrl(Long userIdx) {
        UserInfo userInfo = userInfoRepository.findByUserIdx(userIdx);
        StudyGroup studyGroup = studyGroupRepository.findByUserIdx(userIdx);

        if (userInfo == null || studyGroup == null) {
            throw new RuntimeException("사용자 정보 또는 스터디 그룹 정보를 찾을 수 없습니다.");
        }

        return "/personal/chat/room/" + userInfo.getUserIdx() + "/" + studyGroup.getUserIdx();
    }
}