package com.blog.project.repository;




import com.blog.project.model.Board;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class BoardRepositoryTest {

    private final Logger log = LoggerFactory.getLogger(BoardRepositoryTest.class);
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private EntityManager entityManager;

    @BeforeEach //autu_increment를 테스트 할 때 마다 초기화 시켜줌 !
    public void init(){
        entityManager.createNativeQuery("ALTER TABLE Board AUTO_INCREMENT=1").executeUpdate();


    }
    @Test
    @DisplayName("게시글 작성 테스트")
    public void insertBoard(){
        Board board=new Board(1,"title","content");
        Board boardEntity=boardRepository.save(board);

        assertNotNull(boardEntity);
        assertEquals(board.getTitle(),boardEntity.getTitle());
        log.info("게시글 작성 테스트 성공");

    }

    @Test
    @DisplayName("게시글 수정 테스트")
    public void modifyBaord(){
        Board board=new Board(1,"title","content");
        boardRepository.save(board);

        board.setContent("This is Content");
        Board boardEntity2=boardRepository.save(board);

        assertEquals(board.getContent(),boardEntity2.getContent());
        log.info("게시글 수정 테스트 성공");

    }

    @Test
    @DisplayName("게시글 삭제 테스트")
    public void deleteBoard(){
        Board board=new Board(1,"title","content");
        boardRepository.save(board);
        boardRepository.delete(board);


        assertEquals(Optional.empty(),boardRepository.findById(1));

        log.info("게시글 삭제 테스트 성공");
    }

    @Test
    @DisplayName("게시글 조회 테스트")
    public void selectBoard(){
        Board board=new Board(1,"title","content");
        boardRepository.save(board);

        Board boardEntity= boardRepository.findById(1).get();

        assertNotNull(boardRepository.findById(1));

        assertEquals(board.getTitle(),boardEntity.getTitle());
        assertEquals(board.getContent(),boardEntity.getContent());

        log.info("게시글 조회 테스트 성공");
    }

}
