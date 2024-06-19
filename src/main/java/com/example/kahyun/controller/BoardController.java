package com.example.kahyun.controller;

import com.example.kahyun.VO.BoardVO;
import com.example.kahyun.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import reactor.core.publisher.Flux;

@Controller
@Slf4j
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/list")
    public String boardList(Model model) {
        Flux<BoardVO> boardList = boardService.getAllBoard();
        model.addAttribute("boardList", boardList);
        return "list";
    }
}
