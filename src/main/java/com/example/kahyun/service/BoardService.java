package com.example.kahyun.service;

import com.example.kahyun.VO.BoardVO;
import com.example.kahyun.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    public Flux<BoardVO> getAllBoard() {
        return boardRepository.findAll();
    }

    public Mono<BoardVO> getBoardById(String seq) {
        return boardRepository.findById(seq);
    }

    public Mono<BoardVO> createBoard(BoardVO boardVO) {
        boardVO.setCreate_dt(LocalDateTime.now());
        return boardRepository.save(boardVO);
    }

    public Mono<BoardVO> updateBoard(String seq, BoardVO updateBoardVo) {
        return boardRepository.findById(seq)
                .flatMap(existingBoard -> {
                    existingBoard.setTitle(updateBoardVo.getTitle());
                    existingBoard.setContent(updateBoardVo.getContent());
                    existingBoard.setUpdate_dt(LocalDateTime.now());
                    return boardRepository.save(existingBoard);
                });
    }

    public Mono<Void> deleteBoard(String seq) {
        return boardRepository.deleteById(seq);
    }
}
