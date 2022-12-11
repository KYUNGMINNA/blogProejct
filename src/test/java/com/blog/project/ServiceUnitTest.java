package com.blog.project;

import com.blog.project.model.Board;
import com.blog.project.repository.BoardRepository;
import com.blog.project.service.BoardService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.mockito.Mockito.when;

/**
 *  단위 테스트 - Service 관련 Bean이 IoC등록 되면 됨
 *
 *
 * UserRepository라는 것을 사용해야하는데   이를 사용하려면
 * Bean에 등록해야 하는데  이는 서비스 만 테스트 하는게 아니라 통합테스트가 되는것임
 * 그래서  가짜 객체로 만들 수 있음 ==> MockitoExtension가 지원 해줌
 *
 *
 * @ExtendWith(MockitoExtension.class)
 *
 *
 *
 *
 */

@ExtendWith(MockitoExtension.class)
public class ServiceUnitTest {

    @InjectMocks//BoardService 객체가 만들어 질 때ServiceUnitTest파일에 @Mock로 등록된 모든 애들이 주입 받음
    private BoardService boardService;

    @Mock
    private BoardRepository boardRepository;

    @Test
    public void test(){
        //BODMockito 방식

        //given
        Board board=new Board();
        board.setTitle("title1");
        board.setContent("content1");

        //stub -동작 지정

        when(boardRepository.save(board)).thenReturn(board);


    }


}
