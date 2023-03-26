package com.example.task_management;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;

@Slf4j
@Component
@RequiredArgsConstructor
public class MailComponents {
    private final JavaMailSender javaMailSender;

    public void sendMail(String mail, String subject, String text){
        MimeMessagePreparator msg = new MimeMessagePreparator() {
            @Override
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
                helper.setTo(mail);
                helper.setSubject(subject);
                helper.setText(text);
            }
        };
        javaMailSender.send(msg);
    }
}
