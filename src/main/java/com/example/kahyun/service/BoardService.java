package com.example.kahyun.service;

import com.example.kahyun.VO.BoardVO;
import com.example.kahyun.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
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

    public Mono<BoardVO> getBoardById(String id) {
        return boardRepository.findById(id);
    }

    public Mono<BoardVO> createBoard(BoardVO boardVO) {
        return ReactiveSecurityContextHolder.getContext()
                .doOnNext(securityContext -> {
                    Authentication authentication = securityContext.getAuthentication();
                    String userId = (String) authentication.getPrincipal();

                    boardVO.setCreate_dt(LocalDateTime.now());
                    /*boardVO.setUpdate_dt(LocalDateTime.now());*/
                    boardVO.setAuthorId(userId);
                    System.out.println("service VO : "+ boardVO);
                })
                .then(boardRepository.save(boardVO));
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
