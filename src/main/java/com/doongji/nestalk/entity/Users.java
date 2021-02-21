package com.doongji.nestalk.entity;

import com.doongji.nestalk.entity.user.Email;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@RequiredArgsConstructor
@Entity
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String userId;          //아이디
    private String name;            //이름
    private String password;        //비밀번호
    private String email;           //이메일
    private String phone;           //핸드폰번호
    private String profileImage;    //프로필사진
    private String backgroundImage; //배경사진
    private String stateMessage;    //상태메시지
    private String role;

}
