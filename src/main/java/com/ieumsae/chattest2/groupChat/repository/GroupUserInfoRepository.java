package com.ieumsae.chattest2.groupChat.repository;

import com.ieumsae.chattest2.groupChat.domain.GroupUserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GroupUserInfoRepository extends JpaRepository<GroupUserInfo, Long> {
    /**
     * 사용자 인덱스로 사용자 정보 조회
     * @param userIdx 사용자 인덱스
     * @return 사용자 정보 (Optional)
     */
    Optional<GroupUserInfo> findByUserIdx(int userIdx);

    /**
     * 사용자 이름으로 사용자 정보 조회
     * @param username 사용자 이름
     * @return 사용자 정보 (Optional)
     */
    Optional<GroupUserInfo> findByUserName(String username);
}