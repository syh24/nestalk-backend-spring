package com.doongji.nestalk.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Getter
@RequiredArgsConstructor
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String userId;          // table pk

    @Column(nullable = false)
    private String name;            //이름

    @Column(nullable = false)
    private String password;        //비밀번호

    @Column(nullable = false)
    private String email;           //이메일(id)

    @Column(nullable = false)
    private String phone;           //핸드폰번호

    @Column
    private String profileImage;    //프로필사진

    @Column
    private String backgroundImage; //배경사진

    @Column
    private String stateMessage;    //상태메시지

    private String role;

}
