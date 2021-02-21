package com.doongji.nestalk.vo;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.sql.Timestamp;

@Setter
@Getter
@Entity
//@Table(name = "user")
public class UserVo {
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
    @CreationTimestamp
    private Timestamp createDate;

    public UserVo() {
    }

}
