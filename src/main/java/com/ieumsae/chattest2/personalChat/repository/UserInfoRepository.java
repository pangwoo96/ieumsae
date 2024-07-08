package com.ieumsae.chattest2.personalChat.repository;

import com.ieumsae.chattest2.personalChat.domain.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {

    /**
     * 사용자 idx로 사용자 정보 조회
     *
     * @param userIdx - 사용자 식별자
     * @return 사용자 정보
     */
    UserInfo findByUserIdx(Long userIdx);
}