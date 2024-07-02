package com.example.kahyun.controller;

import com.example.kahyun.VO.BoardVO;
import com.example.kahyun.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/board")
@Slf4j
@RequiredArgsConstructor
public class BoardApiController {

    private final BoardService boardService;

    // 게시글 리스트 출력
    @GetMapping("/list")
    public Flux<BoardVO> getAllBoard() {
        return boardService.getAllBoard();
    }

    // 게시글 상세 출력
    @GetMapping("/detail/{id}")
    public Mono<BoardVO> getBoardById(@PathVariable("id") String id) {
        return boardService.getBoardById(id);
    }

}
