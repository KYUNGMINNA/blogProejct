package com.blog.project.service;


import com.blog.project.model.RoleType;
import com.blog.project.model.User;
import com.blog.project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.Random;


@Service
public class UserService {

    private UserRepository userRepository;
    private BCryptPasswordEncoder encoder;
    private JavaMailSender javaMailSender;
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder encoder, JavaMailSender javaMailSender, RedisTemplate<String, String> redisTemplate) {
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.javaMailSender = javaMailSender;
        this.redisTemplate = redisTemplate;
    }

    @Transactional(readOnly = true)
    public User userSelect(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            return null;
        }
        return user;
    }

    @Transactional
    public void userRegist(User user) {
        String rawPassword = user.getPassword(); //1234원 문
        String encPassword = encoder.encode(rawPassword); //해쉬
        user.setPassword(encPassword);
        user.setRole(RoleType.USER);
        userRepository.save(user);
    }

    @Transactional
    public void userModify(User user) {
        User persistance = userRepository.findById(user.getId()).orElseThrow(
                () -> {
                    return new IllegalArgumentException("회원 찾기 실패");
                });
        if (persistance.getProvider() == null || persistance.getProvider().equals("")) {
            //위의 persistance가 정상적으로 돌아오면 밑에거 수행
            String rawPassword = user.getPassword();
            String encPassword = encoder.encode(rawPassword);
            persistance.setPassword(encPassword);
            persistance.setEmail(user.getEmail());
        }
    }

    public int emailAuth(String accountEmail) {
        Random r = new Random();
        int checkNum = r.nextInt(888888) + 111111;  //111111~999999

        ValueOperations<String, String> stringStringValueOperations = redisTemplate.opsForValue();
        stringStringValueOperations.set("authNum", encoder.encode(Integer.toString(checkNum)),Duration.ofSeconds(180));

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(accountEmail);
        simpleMailMessage.setSubject("회원 가입 인증 비밀 번호 전송");
        simpleMailMessage.setText("인증 번호는 " + checkNum + " 입니다" +
                "해당 인증 번호를 인증란에 입력해 주세요.");
        javaMailSender.send(simpleMailMessage);
        return checkNum;
    }

    public int emailAuthNumberCehck(String authnum) {
        ValueOperations<String, String> stringStringValueOperations=redisTemplate.opsForValue();
        String originalNum=stringStringValueOperations.get("authNum");

        if(encoder.matches(authnum,originalNum)){
                 return 1;
        }
        return 0;
    }
}
