package com.blog.project.repository;



import com.blog.project.ControllerUnitTest;
import com.blog.project.model.User;
import com.mysql.cj.log.LogFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;


@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class UserRepositoryTest {

    private final Logger log = LoggerFactory.getLogger(UserRepositoryTest.class);


    @Autowired
    private UserRepository userRepository;




    @Test
    @DisplayName("회원 가입 테스트")
    public void insertUser(){

        User user=new User(1,"aa","bb","ccc");
        User userEntity=userRepository.save(user);
        assertEquals(user.getUsername(),userEntity.getUsername());
        log.info("회원 가입 테스트 성공 ");
    }

    @Test
    @DisplayName("중복 아이디 체크 테스트")
    public void idCehck(){
        User user=new User(1,"aa","bb","ccc");
        User userEntity=userRepository.save(user);

        User idCheck=userRepository.findByUsername("aa");

        //assertNull();
        //assertNotNull();


        assertNotNull(idCheck);
        log.info("아이디 중복 체크 성공 ");
    }

    @Test
    @DisplayName("회원 정보 변경 테스트")
    public void modifyUser(){
        User user=new User(1,"aa","bb","ccc");
        User userEntity=userRepository.save(user);

        user.setPassword("zzz");
        user.setEmail("ddd");

        User userUpdate=userRepository.save(user);
        assertEquals("zzz",userUpdate.getPassword());
        assertEquals("ddd",userUpdate.getEmail());

        log.info(userUpdate.toString());
        log.info("회원 비밀번호 이메일 변경 테스트 성공 ");

    }
}
