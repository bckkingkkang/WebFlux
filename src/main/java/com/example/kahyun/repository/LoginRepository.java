package com.example.kahyun.repository;

import com.example.kahyun.VO.LoginVO;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface LoginRepository extends ReactiveMongoRepository<LoginVO, Integer> {
    Mono<Long> count();
}

