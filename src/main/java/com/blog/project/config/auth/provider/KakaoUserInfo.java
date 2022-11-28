package com.blog.project.config.auth.provider;

import java.util.Map;

public class KakaoUserInfo implements  OAuth2UserInfo{

    private  Long attributeId;
    private Map<String,Object> attributes;
    private Map<String, Object> attributesAccount;

    public KakaoUserInfo(Map<String, Object> attributes) {
        this.attributes = (Map<String, Object>) attributes.get("kakao_account");
        this.attributesAccount=(Map<String, Object>) attributes.get("profile");
        this.attributeId=(Long) attributes.get("id");
    }

    @Override
    public String getProviderId() {
        return String.valueOf(attributeId);
    }

    @Override
    public String getProvider() {
        return "kakao";
    }

    @Override
    public String getEmail() {
        return (String)attributes.get("email");
    }

    @Override
    public String getName() {
        return (String)attributes.get("nickname");
    }
}
