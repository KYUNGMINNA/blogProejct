package com.blog.project;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

/**
 *  통합 테스트  - 모든 Bean들을 IoC에 올리고 테스트 하는것 (실제로 프로젝트 돌리는 것과 동일)
 *
 * @SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
 * 실제 톰캣을 올리는게 아니라 ,다른 톰켓으로 테스트
 *
 *
 * @SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
 * 실제 톰캣을 올리는것(사용하는 톰캣에)
 *
 * @AutoConfigureMockMvc
 * MockMvc를 IoC에 등록
 *
 * @Transactional
 * 각각 테스트 함수가 종료 될 때 트랜잭션을 Rollback 해줌  -->
 * DB 삽입 테스트 할 때 테스트 종료시 롤백(=초기화)  :: 독립적으로 테스트 할 수 있게 함
 *
 *
 *
 */

/*@Transactional*/
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc // SpringBootTest는 @AutoConfigMockMvc를 들고 있지 않아 직접 선언
public class ControllerIntegreTest {
    private final Logger log = LoggerFactory.getLogger(ControllerIntegreTest.class);
     @Autowired
     private EntityManager entityManager;

     //afterEach 도 있다 ~
   /*  @BeforeEach //autu_increment를 테스트 할 때 마다 초기화 시켜줌 !
    public void init(){
        entityManager.createNativeQuery("ALTER TABLE board AUTO_INCREMENT=1").executeUpdate();


    }*/

    @Test
    public void tt(){
        log.info("message !!!");
        log.info("message !!!");
        log.info("message !!!");
        log.info("message !!!");
        log.info("message !!!");
        log.info("message !!!");
        log.info("message !!!");
        log.info("message !!!");
        log.info("message !!!");
        log.info("message !!!");
        log.info("message !!!");
        log.info("message !!!");
        log.info("message !!!");
        log.info("message !!!");
        log.info("message !!!");
    }


    @Autowired
    private MockMvc mockMvc; //AutoConfigureMockMvc를 선언해야 MockMvc를 의존성 주입 받아 사용 가능
}
