package com.ieumsae.chattest2.groupChat.repository;

import com.ieumsae.chattest2.groupChat.domain.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {
    /**
     * 사용자 인덱스로 사용자 정보 조회
     * @param userIdx 사용자 인덱스
     * @return 사용자 정보 (Optional)
     */
    Optional<UserInfo> findByUserIdx(int userIdx);

    /**
     * 사용자 이름으로 사용자 정보 조회
     * @param username 사용자 이름
     * @return 사용자 정보 (Optional)
     */
    Optional<UserInfo> findByUsername(String username);
}