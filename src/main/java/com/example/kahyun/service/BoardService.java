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
        // 모든 게시판 글 비동기적으로 조회 후 Flux<BoardVO> 형태로 반환
        return boardRepository.findAll();
    }

    public Mono<BoardVO> getBoardById(String id) {
        // 해당 id에 해당하는 게시판 글 비동기적으로 조회 후 Mono<BoardVO> 형태로 반환
        return boardRepository.findById(id);
    }

    // 게시글 등록
    public Mono<BoardVO> createBoard(BoardVO boardVO) {
        return ReactiveSecurityContextHolder.getContext()
                .doOnNext(securityContext -> {
                    Authentication authentication = securityContext.getAuthentication();
                    String userId = (String) authentication.getPrincipal();
                    boardVO.setCreate_dt(LocalDateTime.now());
                    boardVO.setAuthorId(userId);
                })
                .then(boardRepository.save(boardVO));
    }

    public Mono<BoardVO> updateBoard(String id, BoardVO updateBoardVo) {
        return boardRepository.findById(id)
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
