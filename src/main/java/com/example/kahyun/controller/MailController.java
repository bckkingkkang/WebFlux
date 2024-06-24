package com.example.kahyun.controller;

import com.example.kahyun.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class MailController {
    private final MailService mailService;

    @ResponseBody
    @PostMapping("/mail")
    public String mailConfirm(String mail) {
        String data = mailService.sendMail(mail);
        System.out.println(data);
        return data;

    }
}
