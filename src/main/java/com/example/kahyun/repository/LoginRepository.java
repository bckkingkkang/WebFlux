package com.example.kahyun.repository;

import com.example.kahyun.VO.LoginVO;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface LoginRepository extends ReactiveMongoRepository<LoginVO, String> {
    // userId로 모든 LoginVO 객체 조회
    Mono<LoginVO> findByUserId(String userId);

}

