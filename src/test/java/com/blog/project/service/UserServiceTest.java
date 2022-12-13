package com.blog.project.service;

import com.blog.project.model.User;
import com.blog.project.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    private static final Logger log= LoggerFactory.getLogger(UserServiceTest.class);

    @InjectMocks//BoardService 객체가 만들어 질 때ServiceUnitTest파일에 @Mock로 등록된 모든 애들이 주입 받음
    private UserService userService;

    @Mock
    private UserRepository userRepository;
    @Mock
    private BCryptPasswordEncoder encoder;
    @Mock
    private JavaMailSender javaMailSender;
    @Mock
    private RedisTemplate<String, String> redisTemplate;

    @Test
    public  void userIdCheck(){
        //given
        User user=new User(1,"aaa","bbb","ccc");

        //stub --findBYId 는   Optional<User>
         Optional<User> userOP=Optional.of(user);
        when(userRepository.findByUsername(any())).thenReturn(userOP.get());

        //when  --DTO로 받는게 좋다(왜냐면 컨트롤러는  영속성은 컨트롤러까지 가면 안됨)
        User userEntity=userService.userSelect("aaa");

        //then
        assertEquals(userEntity.getUsername(),user.getUsername());






    }


}
