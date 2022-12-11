package com.blog.project;

import com.blog.project.config.auth.PrincipalDetailService;
import com.blog.project.config.oauth.PrincipalOauth2UserService;
import com.blog.project.model.Board;
import com.blog.project.model.User;
import com.blog.project.service.BoardService;
import com.blog.project.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithSecurityContext;
import org.springframework.security.test.context.support.WithSecurityContextFactory;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 *  단위 테스트 -- Controller 관련 로직만 띄우기 ,Filter , ControllerAdivce
 * @WebMvcTest -@ExtendWith({SpringExtension.class})를 구현하고 있어 , Junit5에서는 굳이 선언 안해도되지만
 *   Junit4는  @WebMvcTest + @RunWith(SpringRunner.class)를 함께 선언 해야 함
 *
 *
 * @WebMvcTest는 @AutoConfigMockMvc를 들고 있어 선언 생략 가능
 *
 * @WebMvcTest(클래스이름) 이런식으로 특정 클래스만 사용할 수 있다.
 */

@WebMvcTest //@AutoConfigureMockMvc를 갖고 있어 MockMvc 의존성 주입 받아 사용 가능
public class ControllerUnitTest {


    private final Logger log = LoggerFactory.getLogger(ControllerUnitTest.class);

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PrincipalDetailService principalDetailService;


    @MockBean
    private PrincipalOauth2UserService principalOauth2UserService;
    @MockBean //Ioc 환경에 까자 Bean으로 등록됨 -- Mock이 가짜 객체를 만들어 줌
    private BoardService boardService;

    @MockBean //Ioc 환경에 까자 Bean으로 등록됨 -- Mock이 가짜 객체를 만들어 줌
    private UserService userService;


    @Test
    @DisplayName(" a Test")
    public void a() throws  Exception{
        log.info("gggggggggggggggggggggg");
    }



    @WithMockUser
    //BDDMockito 패턴 - given, when ,then
    @Test
    public void save_테스트() throws Exception {
       log.info("save 테스트 시작===============");


        //given 테스트 하기 위한 준비
       User user=new User(1,"id","pw","email");
       Board board=new Board(1,"title","content");
       boardService.boardWrite(board,user);

        // JSON으로  파라미터 받기 때문에 이렇게 사용
        String content=new ObjectMapper().writeValueAsString(board);


        when(boardService.boardSelect(1)).thenReturn(new Board(1,"title","content"));


       // when(boardService.boardWrite(board,user)).thenReturn(new Board((1,"title","content")));


        //when 테스트 실행                           //get ,post ...등등 다양하게 있음
        ResultActions resultActions=mockMvc.perform(get("/board/all/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(content)
                .accept(MediaType.APPLICATION_JSON_UTF8)
        );

        //then 검증   $.으로 JSON의 key값을 검색  jsonPath("$.title").exists() 이런것도 있음
        resultActions.andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("title"))
                        .andDo(MockMvcResultHandlers.print());
        // $는 json 객체 파싱하는방법 --json path을 검색해보자
    }


    @Test
    public void findAll_테스트(@PageableDefault(size = 3,sort = "id",direction = Sort.Direction.DESC) Pageable pageable)throws  Exception{
        //given
        List<Board> boards=new ArrayList<>();
        boards.add(new Board(1,"title1","content1"));
        boards.add(new Board(2,"title2","content2"));
        when(boardService.boardList(pageable)).thenReturn((Page<Board>) boards);


        //when
        ResultActions resultActions=mockMvc.perform(get("")
                .accept(MediaType.APPLICATION_JSON_UTF8));

        //then
        resultActions
                .andExpect(status().isOk()) //기대하는 값
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$[0].title").value("title1"))
                .andDo(MockMvcResultHandlers.print());
    }


    @Test
    public  void  findById_테스트() throws Exception{
        //given
        int id=1;
        when(boardService.boardSelect(id)).thenReturn(new Board(1,"title","content"));

        //when
        ResultActions resultActions=mockMvc.perform(get("/board/all/{id}",id)
                .accept(MediaType.APPLICATION_JSON_UTF8));

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("title"))
                .andDo(MockMvcResultHandlers.print()); //프로그램 따라 다르게 써야함  이프로젝트에서는 200을 리턴

    }


    @Test
    public void 지우기테스트() throws Exception{
        //givne
        int id=1;
        //when(boardService.boardDelete(id)).thenReturn("ok");

        //when
        ResultActions resultActions=mockMvc.perform(delete("/book/id",id)
            .accept(MediaType.TEXT_PLAIN)); //String return이면 이렇게 쓴다

        //then
        resultActions.andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());


        MvcResult requestResult=resultActions.andReturn();
        String result=requestResult.getResponse().getContentAsString();

        assertEquals("ok",result);




    }


}
