package com.blog.project.repository;


import com.blog.project.model.Board;
import com.blog.project.model.Reply;
import com.blog.project.model.User;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class ReplyRepositoryTest {

    private final Logger log = LoggerFactory.getLogger(ReplyRepositoryTest.class);
    @Autowired
    private ReplyReposiotry replyReposiotry;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EntityManager entityManager;

    @BeforeEach //autu_increment를 테스트 할 때 마다 초기화 시켜줌 !
    public void init(){
        entityManager.createNativeQuery("ALTER TABLE Reply AUTO_INCREMENT=1").executeUpdate();
        entityManager.createNativeQuery("ALTER TABLE Board AUTO_INCREMENT=1").executeUpdate();
        entityManager.createNativeQuery("ALTER TABLE User AUTO_INCREMENT=1").executeUpdate();


    }
    @Test
    @DisplayName("답글 작성 테스트")
    public void insertBoard(){
        User user=new User(1,"aa","bb","ccc");
        User userEntity=userRepository.save(user);

        Board board=new Board(1,"title","content");
        boardRepository.save(board);


        Reply reply=Reply.builder().id(1).content("aaa").board(board).user(user).build();
        Reply replyEntity=replyReposiotry.save(reply);

        assertNotNull(replyEntity);
        assertEquals(reply.getContent(),replyEntity.getContent());
        log.info("답글 작성 테스트 성공");

        log.info(replyEntity.toString());
    }

    @Test
    @DisplayName("답글 수정 테스트")
    public void modifyBaord(){
        User user=new User(1,"aa","bb","ccc");
        User userEntity=userRepository.save(user);

        Board board=new Board(1,"title","content");
        boardRepository.save(board);


        Reply reply=Reply.builder().id(1).content("aaa").board(board).user(user).build();
        replyReposiotry.save(reply);
        reply.setContent("ddddddddd");
        Reply replyEntity=replyReposiotry.save(reply);

        assertEquals(reply.getContent(),replyEntity.getContent());
        log.info("답글 수정 테스트 성공");

    }

    @Test
    @DisplayName("답글 삭제 테스트")
    public void deleteBoard(){
        User user=new User(1,"aa","bb","ccc");
        User userEntity=userRepository.save(user);

        Board board=new Board(1,"title","content");
        boardRepository.save(board);


        Reply reply=Reply.builder().id(1).content("aaa").board(board).user(user).build();
        replyReposiotry.save(reply);
        replyReposiotry.delete(reply);


        assertEquals(Optional.empty(),replyReposiotry.findById(1));

        log.info("답글 삭제 테스트 성공");
    }

    @Test
    @DisplayName("답글 조회 테스트")
    public void selectBoard(){

        User user=new User(1,"aa","bb","ccc");
        User userEntity=userRepository.save(user);

        Board board=new Board(1,"title","content");
        boardRepository.save(board);


        Reply reply=Reply.builder().id(1).content("aaa").board(board).user(user).build();
        replyReposiotry.save(reply);


        assertNotNull(replyReposiotry.findById(1));

        log.info(replyReposiotry.findById(1).toString());

        log.info("답글 조회 테스트 성공");
    }

}
