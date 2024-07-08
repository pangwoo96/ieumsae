package com.ieumsae.chattest2.personalChat.repository;

import com.ieumsae.chattest2.personalChat.domain.StudyGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudyGroupRepository extends JpaRepository<StudyGroup, Long> {

    /**
     * 사용자 idx로 스터디 그룹 정보 조회
     *
     * @param userIdx - 사용자 식별자
     * @return 스터디 그룹 정보
     */
    StudyGroup findByUserIdx(Long userIdx);
}