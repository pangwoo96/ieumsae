package com.ieumsae.chattest2.groupChat.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "USER_INFO")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idx")
    private Long id;

    @Column(name = "user_idx") // 회원번호
    private int userIdx;

    @Column(name = "user_nick_name") // 닉네임
    private String userNickName;

}