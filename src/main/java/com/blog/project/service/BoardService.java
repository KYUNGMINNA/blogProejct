package com.blog.project.service;


import com.blog.project.model.Board;
import com.blog.project.model.ReplySaveRequestDto;
import com.blog.project.model.User;
import com.blog.project.repository.BoardRepository;
import com.blog.project.repository.ReplyReposiotry;
import com.blog.project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class BoardService {


    private BoardRepository boardRepository;
    private ReplyReposiotry replyReposiotry;

    @Autowired
    public BoardService(BoardRepository boardRepository, ReplyReposiotry replyReposiotry) {
        this.boardRepository = boardRepository;
        this.replyReposiotry = replyReposiotry;
    }

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void boardWrite(Board board, User user) {
        board.setCount(0);
        board.setUser(user);
        boardRepository.save(board);
    }

    @Transactional(readOnly = true)
    public Page<Board> boardList(Pageable pageable) {
        return boardRepository.findAll(pageable);
    }


    @Transactional(readOnly = true)
    public Board boardSelect(int id) {
        return boardRepository.findById(id).orElseThrow(() -> {
            return new IllegalArgumentException("글 상세보기 실패 :아이디를 찾을 수 없습니다.");
        });
    }

    @Transactional
    public void boardDelete(int id) {
        boardRepository.deleteById(id);
    }


    @Transactional
    public void boardModify(int id, Board requestBoard) {
        Board board = boardRepository.findById(id).orElseThrow(() -> {
            return new IllegalArgumentException("글 찾기 실패 :아이디를 찾을 수 없습니다.");
        });
        board.setTitle(requestBoard.getTitle());
        board.setContent(requestBoard.getContent());

    }

    @Transactional

    public void replyWrite(ReplySaveRequestDto replySaveRequestDto) {
        replyReposiotry.mSave(replySaveRequestDto.getUserId(), replySaveRequestDto.getBoardId(), replySaveRequestDto.getContent());
    }

    @Transactional
    public void replyDelete(int replyId) {
        replyReposiotry.deleteById(replyId);
    }


}
