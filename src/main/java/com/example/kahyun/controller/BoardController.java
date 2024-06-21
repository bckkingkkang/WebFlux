package com.example.kahyun.controller;

import com.example.kahyun.VO.BoardVO;
import com.example.kahyun.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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
    public Mono<String> createBoard(Model model) {
        return Mono.just("board/create");
    }

    @ResponseBody
    @PostMapping("/board/createBoard")
    public Mono<String> createBoard(BoardVO boardVO) {
        boardService.createBoard(boardVO).subscribe();
        return Mono.just("redirect:/board/list");
    }

    @GetMapping("/board/detail/{seq}")
    public Mono<String> detailBoard(@PathVariable("seq") String seq) {
        boardService.getBoardById(seq).subscribe();

        return Mono.just("board/detail");
    }


}
