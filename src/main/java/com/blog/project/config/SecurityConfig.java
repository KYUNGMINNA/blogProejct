package com.blog.project.config;

import com.blog.project.config.auth.PrincipalDetailService;
import com.blog.project.config.oauth.PrincipalOauth2UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig  extends WebSecurityConfigurerAdapter {
    private PrincipalDetailService principalDetailService;
    private PrincipalOauth2UserService principalOauth2UserService;

    @Autowired
    public SecurityConfig(PrincipalDetailService principalDetailService, PrincipalOauth2UserService principalOauth2UserService) {
        this.principalDetailService = principalDetailService;
        this.principalOauth2UserService = principalOauth2UserService;
    }

    public SecurityConfig(boolean disableDefaults, PrincipalDetailService principalDetailService, PrincipalOauth2UserService principalOauth2UserService) {
        super(disableDefaults);
        this.principalDetailService = principalDetailService;
        this.principalOauth2UserService = principalOauth2UserService;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManagerBean();
    }
    @Bean
    public BCryptPasswordEncoder encodePWD() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(principalDetailService).passwordEncoder(encodePWD());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //antMatchers 를 제외한 모든 페이지는 인증이 되야 접속이 가능한 페이지

        http     //스프링 시큐리티는 요청시에 csrf 토큰을 가지고 있지 않으면 동작을 막음 그래서 disalbe해야함
                .csrf().disable() //csrf 토큰 비활성화 (테스트시 걸어두는게 좋음) -나중엔 없애야 함
                .authorizeHttpRequests()
                .antMatchers("/", "/auth/**", "/js/**", "/css/**", "/image/**","/dummy/**","/check/**")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .loginPage("/auth/loginForm")
                .loginProcessingUrl("/auth/loginProc") //스프링 시큐리티가 해당 주소로 요청이 오는 로그인 가로채서 대신로그인 해줌
                .defaultSuccessUrl("/")
                .and()
                .oauth2Login()
                .loginPage("/loginForm") //구글 로그인이 완료된 뒤의 후처리가 필요함 Tip.코드X,(액세스 토큰 +사용자프리필정보 O)
                .userInfoEndpoint()
                .userService(principalOauth2UserService);


    }





}


