package com.example.kahyun.controller;

import com.example.kahyun.VO.BoardVO;
import com.example.kahyun.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
@Slf4j
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    // 게시글 리스트 화면
    @GetMapping("/board/list")
    public Mono<String> boardList() {
        return Mono.just("/board/list");
    }

    // 게시글 상세 화면
    @GetMapping("/board/detail/{id}")
    public Mono<String> boardDetail() {
        return Mono.just("/board/detail");
    }

    // 게시글 수정 화면
    @GetMapping("/board/edit/{id}")
    public Mono<String> editBoard() {
        return Mono.just("/board/edit");
    }

    // 게시글 등록 화면
    @GetMapping("/board/create")
    public Mono<String> createBoard() {
        return Mono.just("board/create");
    }

    // 게시글 등록
    @PostMapping("/board/create")
    public Mono<BoardVO> createBoard(@RequestBody BoardVO boardVO) {
        return boardService.createBoard(boardVO);
    }

    // 게시글 수정
    @PostMapping("/board/edit")
    public Mono<BoardVO> updateBoard(@RequestBody BoardVO boardVO) {
        return boardService.updateBoard(boardVO);
    }

    // 게시글 삭제



}
