package com.blog.project;


import com.blog.project.model.Board;
import com.blog.project.repository.BoardRepository;
import com.blog.project.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 *  단위 테스트 - DB관런 Bean이 IoC에 등록되면 됨
 *
 *@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
 * 까자 DB로 테스트
 *
 *
 * @AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
 * 실제 DB로 테스트
 *
 *
 * @DataJpaTest
 * Repository를 Ioc에 등록해줌
 */


@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@DataJpaTest //@ExtendWith({SpringExtension.class})을 갖고 있어 Mock 사용 안해도 됨
public class RepositoryUnitTest {

    private BoardRepository boardRepository;

    @Test
    public void save_테스트(){
        //given
        Board board=new Board(1,"title1","content1");


        //when
        Board boardEntity=boardRepository.save(board);

        //then
        assertEquals("title1",boardEntity.getTitle());
    }



}
