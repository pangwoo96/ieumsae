package com.ieumsae.chattest2.groupChat.repository;

import com.ieumsae.chattest2.groupChat.domain.StudyGroupRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudyGroupRequestRepository extends JpaRepository<StudyGroupRequest, Long> {
    /**
     * 사용자 인덱스, 스터디 인덱스, 신청 상태로 스터디 그룹 요청 존재 여부 확인
     * @param userIdx 사용자 인덱스
     * @param studyIdx 스터디 인덱스
     * @param studyReqStatus 신청 상태
     * @return 존재 여부 (boolean)
     */
    boolean existsByUserIdxAndStudyIdxAndStudyReqStatus(int userIdx, int studyIdx, short studyReqStatus);
}