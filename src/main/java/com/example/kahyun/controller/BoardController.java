package com.example.kahyun.controller;

import com.example.kahyun.VO.BoardVO;
import com.example.kahyun.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
        System.out.println(boardListFlux);
        return boardListFlux.collectList()
                .flatMap(boardList -> {
                    model.addAttribute("boardList", boardList);
                    return Mono.just("board/list");
                });
    }

}
