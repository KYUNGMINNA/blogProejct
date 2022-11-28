package com.blog.project.controller.api;


import com.blog.project.dto.ResponseDto;
import com.blog.project.model.User;
import com.blog.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@RestController
public class UserApiController {

    private UserService userService;
    private AuthenticationManager authenticationManager;



    @Autowired
    public UserApiController(UserService userService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;

    }

    @PostMapping("/auth/joinProc")
    public ResponseDto<Integer> save(@RequestBody User user){
        userService.userRegist(user);
        return new ResponseDto<Integer>(HttpStatus.OK.value(),1);
    }

    @PutMapping("/user")
    public ResponseDto<Integer> update(@RequestBody User user){
        userService.userModify(user);
        Authentication authentication=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new ResponseDto<Integer>(HttpStatus.OK.value(),1);
    }

    @GetMapping("/check/id/{username}")
    public ResponseDto<Integer> save(@PathVariable String username){
        if (userService.userSelect(username)!=null){
            return new ResponseDto<Integer>(HttpStatus.NOT_FOUND.value(), 1);
        }else{
            return new ResponseDto<Integer>(HttpStatus.OK.value(),1);
        }
    }

    @GetMapping("/check/email/{email}")
    public  ResponseDto<Integer> emailAuth(@PathVariable String email ){
        return new ResponseDto<Integer>(HttpStatus.OK.value(),userService.emailAuth(email));
    }

    @GetMapping("/check/emailauthnum/{authnum}")
    public ResponseDto<Integer> emailAuthNumercheck(@PathVariable String authnum){
       int successNum=userService.emailAuthNumberCehck(authnum);
       if(successNum==1){
        return new ResponseDto<Integer>(HttpStatus.OK.value(),1);
       }else{
        return new ResponseDto<Integer>(HttpStatus.INTERNAL_SERVER_ERROR.value(),1);
       }

    }

}
