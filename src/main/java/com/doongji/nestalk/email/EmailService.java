package com.doongji.nestalk.email;

import com.doongji.nestalk.entity.user.User;
import com.doongji.nestalk.error.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.UUID;


@RequiredArgsConstructor
@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public void mailSend (String to, String sub, String text){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(sub);
        message.setText(text);
        mailSender.send(message);
    }

    public String sendTemporaryPassword(User user) throws NotFoundException{
        UUID uuid = UUID.randomUUID();
        String temp = uuid.toString().substring(0, 8);
        mailSend(user.getEmail(),"[NESTALK] 임시 비밀번호 입니다.","임시 비밀번호는 : " + temp + "입니다.");
        return temp;
    }
}
