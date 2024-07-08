package com.ieumsae.chattest2.groupChat.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "STUDT_GROUP_REQUEST")
public class StudyGroupRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idx")
    private Long id;

    @Column(name = "study_idx") // 스터디 번호
    private int studyIdx;

    @Column(name = "user_idx") // 신청자 회원 번호
    private int userIdx;

    @Column(name = "study_req_status") // 회원 신청 상태
    private short studyReqStatus;

}
