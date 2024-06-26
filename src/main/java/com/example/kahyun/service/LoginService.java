package com.example.kahyun.service;

import com.example.kahyun.VO.LoginVO;
import com.example.kahyun.repository.LoginRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final LoginRepository loginRepository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public PasswordEncoder passwordEncoder() {
        return passwordEncoder;
    }

    public Mono<LoginVO> registerUser(LoginVO loginVO) {
        loginVO.setAuth("USER");
        // 저장
        return loginRepository.save(loginVO);
    }

    public Mono<LoginVO> findByUserId(String userId) {
        return loginRepository.findByUserId(userId);
    }

}
