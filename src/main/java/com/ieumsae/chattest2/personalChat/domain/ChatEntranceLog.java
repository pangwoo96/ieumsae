package com.ieumsae.chattest2.personalChat.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "CHAT_ENTRANCE_LOG")
public class ChatEntranceLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idx")
    private Long id;

    @Column(name = "chat_idx")
    private Long chatIdx;

    @Column(name = "user_idx")
    private Long userIdx;
}
