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

    // 게시판 리스트 화면
    @GetMapping("/board/list")
    public Mono<String> boardList(Model model) {
        // 게시판 목록 Flux<BoardVO> -> 비동기로 가져옴
        Flux<BoardVO> boardListFlux = boardService.getAllBoard();

        // Flux 를 List 로 변환 후 model 에 추가
        /*
            collectList() : Flux -> List, Flux 가 발행하는 모든 항목을 수집하여 List 로 만들어주는 연산자
        */
        return boardListFlux.collectList()
                /*
                    - flatMap

                    1. 비동기 데이터 변환
                        : 비동기적으로 처리되는 데이터나 이벤트를 다른 형태로 변환하거나 조작할 때 사용
                    2. 중첩된 Mono/Flux 처리
                        : 한 번에 여러 개의 데이터를 처리하고, 그 결과를 하나의 Mono나 Flux로 조립할 때 사용
                    3. 병렬 처리
                        : 여러 개의 Flux를 병렬로 처리하고 결과를 합치는 경우에 사용
                    4. 에러 처리
                        : 에러가 발생했을 때 대체 데이터를 제공하거나 다른 처리 방식 적용 가능
                */

                .flatMap(boardList -> {
                    model.addAttribute("boardList", boardList);
                    // "/board/list" 뷰 반환
                    return Mono.just("board/list");
                });
    }

    // 게시글 상세 화면
    @GetMapping("/board/detail/{id}")
    public Mono<String> detailBoard(@PathVariable("id") String id, Model model) {
        return boardService.getBoardById(id)
                .doOnNext(board -> model.addAttribute("board", board))
                .then(Mono.just("/board/detail"));
    }

    // 게시글 수정 화면
    @GetMapping("/board/edit/{id}")
    public Mono<String> editBoard(@PathVariable("id") String id, Model model) {
        return boardService.getBoardById(id)
                .doOnNext(board -> model.addAttribute("board", board))
                .then(Mono.just("/board/edit"));
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
    @PostMapping("/board/update")
    public Mono<BoardVO> updateBoard(@RequestBody BoardVO boardVO) {
        return boardService.updateBoard(boardVO.getId(), boardVO);
    }

    // 게시글 삭제



}
