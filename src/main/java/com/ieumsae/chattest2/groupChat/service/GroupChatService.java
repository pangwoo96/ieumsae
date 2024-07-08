package com.ieumsae.chattest2.groupChat.service;

import com.ieumsae.chattest2.groupChat.domain.GroupChat;
import com.ieumsae.chattest2.groupChat.domain.UserInfo;
import com.ieumsae.chattest2.groupChat.repository.GroupChatRepository;
import com.ieumsae.chattest2.groupChat.repository.StudyGroupRequestRepository;
import com.ieumsae.chattest2.groupChat.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class GroupChatService {

    private final StudyGroupRequestRepository studyGroupRequestRepository;
    private final GroupChatRepository groupChatRepository;
    private final UserInfoRepository userInfoRepository;
    private final SimpMessagingTemplate messagingTemplate;

    /**
     * GroupChatService 생성자
     *
     * @param studyGroupRequestRepository 스터디 그룹 요청 레포지토리
     * @param groupChatRepository         그룹 채팅 레포지토리
     * @param userInfoRepository          사용자 정보 레포지토리
     * @param messagingTemplate           메시징 템플릿
     */
    @Autowired
    public GroupChatService(StudyGroupRequestRepository studyGroupRequestRepository,
                            GroupChatRepository groupChatRepository,
                            UserInfoRepository userInfoRepository,
                            SimpMessagingTemplate messagingTemplate) {
        this.studyGroupRequestRepository = studyGroupRequestRepository;
        this.groupChatRepository = groupChatRepository;
        this.userInfoRepository = userInfoRepository;
        this.messagingTemplate = messagingTemplate;
    }

    /**
     * 사용자가 채팅방에 입장할 수 있는지 확인
     *
     * @param userIdx  사용자 인덱스
     * @param studyIdx 스터디 인덱스
     * @return 입장 가능 여부
     */
    public boolean canEnterChatRoom(int userIdx, int studyIdx) {
        return studyGroupRequestRepository.existsByUserIdxAndStudyIdxAndStudyReqStatus(userIdx, studyIdx, (short) 2);
    }

    /**
     * 채팅방 입장 처리
     *
     * @param message 그룹 채팅 메시지
     */
    public void processRoomEntry(GroupChat message) {
        if (canEnterChatRoom(message.getUserIdx(), message.getStudyIdx())) {
            UserInfo userInfo = userInfoRepository.findByUserIdx(message.getUserIdx())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            message.setChatContent(userInfo.getUserNickName() + " 님이 입장하셨습니다.");
            message.setChatIdx(generateChatIdx(message.getUserIdx(), message.getStudyIdx()));

            groupChatRepository.save(message);

            messagingTemplate.convertAndSend("/group/chat/room/" + message.getStudyIdx(), message);

            List<GroupChat> previousMessages = groupChatRepository.findByStudyIdxOrderByChatSendDtAsc(message.getStudyIdx());
            messagingTemplate.convertAndSendToUser(String.valueOf(message.getUserIdx()), "/personal/chat/room", previousMessages);
        } else {
            messagingTemplate.convertAndSendToUser(String.valueOf(message.getUserIdx()), "/personal/chat/room", "스터디 그룹에 속해있지 않아 입장할 수 없습니다.");
        }
    }

    /**
     * 메시지 전송 처리
     *
     * @param message 그룹 채팅 메시지
     */
    public void sendMessage(GroupChat message) {
        UserInfo userInfo = userInfoRepository.findByUserIdx(message.getUserIdx())
                .orElseThrow(() -> new RuntimeException("User not found"));

        message.setChatIdx(generateChatIdx(message.getUserIdx(), message.getStudyIdx()));
        groupChatRepository.save(message);

        messagingTemplate.convertAndSend("/group/chat/room/" + message.getStudyIdx(), message);
    }

    /**
     * 채팅 인덱스 생성
     *
     * @param userIdx  사용자 인덱스
     * @param studyIdx 스터디 인덱스
     * @return 생성된 채팅 인덱스
     */
    private int generateChatIdx(int userIdx, int studyIdx) {
        return Integer.parseInt("2" + String.format("%03d", userIdx) + String.format("%05d", studyIdx));
    }

    /**
     * 사용자 정보 조회
     *
     * @param username 사용자 이름
     * @return 사용자 정보
     */
    public UserInfo getUserInfo(String username) {
        return userInfoRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}