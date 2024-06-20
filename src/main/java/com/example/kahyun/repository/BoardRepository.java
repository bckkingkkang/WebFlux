package com.example.kahyun.repository;

import com.example.kahyun.VO.BoardVO;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface BoardRepository extends ReactiveMongoRepository<BoardVO, String> {
    // MongoDB 데이터베이스에서 create_dt 를 기준으로 내림차순 정렬
    /*Flux<BoardVO> findAllByOrderByCreateDtDesc();*/
}
