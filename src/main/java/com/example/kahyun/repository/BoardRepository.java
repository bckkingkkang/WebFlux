package com.example.kahyun.repository;

import com.example.kahyun.VO.BoardVO;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface BoardRepository extends ReactiveMongoRepository<BoardVO, String> {
}
