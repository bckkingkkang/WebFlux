package com.example.kahyun.repository;

import com.example.kahyun.VO.LoginVO;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface LoginRepository extends ReactiveMongoRepository<LoginVO, String> {

    Mono<LoginVO> findByUser_id(String user_id);

}

