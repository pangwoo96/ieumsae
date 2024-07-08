package com.ieumsae.chattest2.groupChat.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "GROUP_CHAT")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GroupChat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(name = "study_idx") // 스터디 번호
    private int studyIdx;

    @Column(name = "chat_idx") // 채팅방 번호
    private int chatIdx;

    @Column(name = "user_idx") // 발신 회원 번호
    private int userIdx;

    @Column(name = "chat_content") // 채팅 내용
    private String chatContent;
}