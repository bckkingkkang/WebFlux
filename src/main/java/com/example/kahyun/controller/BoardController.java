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

    @GetMapping("/board/list")
    public Mono<String> boardList(Model model) {
        Flux<BoardVO> boardListFlux = boardService.getAllBoard();
        log.info("board list : {}", boardListFlux);
        // Flux 를 List 로 변환
        return boardListFlux.collectList()
                .flatMap(boardList -> {
                    model.addAttribute("boardList", boardList);
                    return Mono.just("board/list");
                });
    }

    @GetMapping("/board/create")
    public Mono<String> createBoard() {
        return Mono.just("board/create");
    }

    @PostMapping("/board/create")
    public Mono<BoardVO> createBoard(@RequestBody BoardVO boardVO) {
        return boardService.createBoard(boardVO);
    }

    @GetMapping("/board/detail/{seq}")
    public Mono<String> detailBoard(@PathVariable("seq") String seq) {
        boardService.getBoardById(seq).subscribe();

        return Mono.just("board/detail");
    }


}
