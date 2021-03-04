package com.doongji.nestalk.email;

import com.doongji.nestalk.entity.user.User;
import com.doongji.nestalk.error.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;


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

        StringBuffer temp =new StringBuffer();
        Random rnd = new Random();
        for(int i=0;i<10;i++) {
            int rIndex = rnd.nextInt(3);
            switch (rIndex) {
                case 0:
                    // a-z
                    temp.append((char) ((int) (rnd.nextInt(26)) + 97));
                    break;
                case 1:
                    // A-Z
                    temp.append((char) ((int) (rnd.nextInt(26)) + 65));
                    break;
                case 2:
                    // 0-9
                    temp.append((rnd.nextInt(10)));
                    break;
            }
        }
        mailSend(user.getEmail(),"[NESTALK] 비밀번호 찾기를 위한 인증메일입니다.", "임시 비밀번호는 :" + temp.toString() + "입니다.");
        return temp.toString();
    }
}
