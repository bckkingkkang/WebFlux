package com.example.kahyun.controller;

import com.example.kahyun.VO.BoardVO;
import com.example.kahyun.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/board")
@Slf4j
@RequiredArgsConstructor
public class BoardApiController {

    private final BoardService boardService;

    @GetMapping("/list")
    public Flux<BoardVO> getAllBoard() {
        return boardService.getAllBoard();
    }
}
