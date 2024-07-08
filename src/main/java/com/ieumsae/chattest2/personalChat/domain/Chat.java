package com.ieumsae.chattest2.personalChat.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "CHAT")
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idx")
    private Long id;

    @Column(name = "chat_idx")
    private Long chatIdx;

    @Column(name = "user_idx")
    private Long userIdx;

    @Column(name = "chat_content")
    private String chatContent;

    @Column(name = "chat_send_dt")
    private Date chatSendDt;

}
