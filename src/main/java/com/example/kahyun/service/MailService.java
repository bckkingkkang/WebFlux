package com.example.kahyun.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Random;

@Log4j2
@Service
public class MailService {

    private static String number;

    private JavaMailSender javaMailSender;

    public static void createNumber(){
        Random random = new Random();
        StringBuffer key = new StringBuffer();

        for(int i=0; i<8; i++) { // 총 8자리 인증 번호 생성
            int idx = random.nextInt(3); // 0~2 사이의 값을 랜덤하게 받아와 idx에 집어넣습니다

            // 0,1,2 값을 switchcase를 통해 꼬아버립니다.
            // 숫자와 ASCII 코드를 이용합니다.
            switch (idx) {
                case 0 :
                    // 0일 때, a~z 까지 랜덤 생성 후 key에 추가
                    key.append((char) (random.nextInt(26) + 97));
                    break;
                case 1:
                    // 1일 때, A~Z 까지 랜덤 생성 후 key에 추가
                    key.append((char) (random.nextInt(26) + 65));
                    break;
                case 2:
                    // 2일 때, 0~9 까지 랜덤 생성 후 key에 추가
                    key.append(random.nextInt(9));
                    break;
            }
        }
        number = key.toString();
    }

    public MimeMessage createMessage(String email){
        createNumber();
        log.info("Number : {}",number);

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try{
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true); // Helper 사용
            messageHelper.setFrom("bufar1014@gmail.com");
            messageHelper.setTo(email);
            messageHelper.setSubject("이메일 인증 번호 발송");

            String body = "<html>";
            body += "<h1 style='padding-top: 50px; font-size: 30px;'>이메일 주소 인증</h1>";
            body += "<p>회원 가입을 위해 하단에 이메일 인증을 완료해주시길 바랍니다.<br />";
            body += "감사합니다.</p>";
            body += "<h3>" + number + "</h3>";
            body += "</body></html>";
            messageHelper.setText(body, true);
        }catch (MessagingException e){
            e.printStackTrace();
        }
        return mimeMessage;
    }

    public String sendMail(String email) {
        MimeMessage mimeMessage = createMessage(email);
        log.info("[Mail 전송 시작]");
        javaMailSender.send(mimeMessage);
        log.info("[Mail 전송 완료]");
        return number;
    }


}
