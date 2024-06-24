package com.example.kahyun.service;

import com.example.kahyun.VO.LoginVO;
import com.example.kahyun.repository.LoginRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final LoginRepository loginRepository;
    private final PasswordEncoder passwordEncoder;


    public Mono<LoginVO> registerUser(LoginVO loginVO) {

        return loginRepository.save(loginVO);
    }

}
