
package com.blog.project.config.oauth;//package com.sale.shop.config.oauth;



import com.blog.project.config.auth.PrincipalDetail;
import com.blog.project.config.auth.provider.GoogleUserInfo;
import com.blog.project.config.auth.provider.KakaoUserInfo;
import com.blog.project.config.auth.provider.NaverUserInfo;
import com.blog.project.config.auth.provider.OAuth2UserInfo;
import com.blog.project.model.RoleType;
import com.blog.project.model.User;
import com.blog.project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    private UserRepository userRepository;

    @Autowired
    public PrincipalOauth2UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User=super.loadUser(userRequest);
        OAuth2UserInfo oAuth2UserInfo=null;
        if(userRequest.getClientRegistration().getRegistrationId().equals("google")){
            oAuth2UserInfo=new GoogleUserInfo(oAuth2User.getAttributes());
        }else  if(userRequest.getClientRegistration().getRegistrationId().equals("naver")){
            oAuth2UserInfo=new NaverUserInfo((Map)oAuth2User.getAttributes().get("response"));
        }else  if(userRequest.getClientRegistration().getRegistrationId().equals("kakao")){
            oAuth2UserInfo=new KakaoUserInfo(oAuth2User.getAttributes());
        }else{
            System.out.println("????????? ??????????????? ???????????? ???????????? ????????????");
        }

        String provider=oAuth2UserInfo.getProvider();
        String providerId=oAuth2UserInfo.getProviderId();
        String username=provider+"_"+providerId;
        String password="rkskekfkakqktk"; //?????? ?????? ?????? ??????
        String email=oAuth2UserInfo.getEmail();
        String role="ROLE_USER";

        User userEntity=userRepository.findByUsername(username);

        if(userEntity==null){
            System.out.println("OAuth ???????????? ???????????????.");
            userEntity=User.builder()
                    .username(username)
                    .password(password)
                    .email(email)
                    .role(RoleType.USER)
                    .provider(provider)
                    .providerId(providerId)
                    .build();
            userRepository.save(userEntity);
        }else{
            System.out.println("???????????? ?????? ?????????????????????.????????? ?????? ??????????????? ??????????????????.");
        }
        return new PrincipalDetail(userEntity,oAuth2User.getAttributes());
    }
}

