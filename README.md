# blogProejct



<h3>시큐리티 설정하여 페이지 접근 권한과 ,로그인 화면 설정</h3>

```JAVA
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
        http
                .csrf().disable()
                .authorizeHttpRequests()
                .antMatchers("/", "/auth/**", "/js/**", "/css/**", "/image/**","/dummy/**","/check/**","/board/all/**")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .loginPage("/auth/loginForm")
                .loginProcessingUrl("/auth/loginProc")
                .defaultSuccessUrl("/")
                .and()
                .oauth2Login()
                .loginPage("/loginForm")
                .userInfoEndpoint()
                .userService(principalOauth2UserService);
    }
}

```


<h3> Securiy의 Authentication 객체를  사용하기 위해  해당 인터페이스를 구현한다.</h3>


```JAVA 
@Getter
public class PrincipalDetail implements UserDetails, OAuth2User {  
    private User user;
    private Map<String,Object> attributes;

    public PrincipalDetail(User user) {
        this.user=user;
    }

    public PrincipalDetail(User user, Map<String, Object> attributes) {
        this.user = user;
        this.attributes = attributes;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
       Collection<GrantedAuthority> collectors=new ArrayList<>();
       collectors.add(()->{ return "ROLE_"+user.getRole();});
        return collectors;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }
    @Override
    public String getName() {
        return null;
    }
}

```


```JAVA
@Service 
public class PrincipalDetailService implements UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    public PrincipalDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User principal = userRepository.findByUsername(username);
        if (principal != null) {
            return new PrincipalDetail(principal);
        }

        return null;
    }
}
```


<h3>OAtuth2를 사용하여 소셜 로그인을 구현한다.  요청을 통해 응받으로 받은 토큰을 활용하여 , 로그인 한 사용자의 정보를 요청한다 
정보가 정상적으로 오면 자동으로 회원가입을 진행하며 , 이전에 가입했던 기록이 있으면 추가적인 회원가입 없이 
</h3>
```JAVA
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
            System.out.println("구글 네이버 카카오만 지원해요");
        }
        String provider=oAuth2UserInfo.getProvider();
        String providerId=oAuth2UserInfo.getProviderId();
        String username=provider+"_"+providerId;
        String password="rkskekfkakqktk";
        String email=oAuth2UserInfo.getEmail();
        User userEntity=userRepository.findByUsername(username);

        if(userEntity==null){
            System.out.println("OAuth 로그인이 최초입니다.");
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
            System.out.println("로그인을 이미 한적이있습니다.당신은 자동 회원가입이 되어있습니다.");
        }
        return new PrincipalDetail(userEntity,oAuth2User.getAttributes());
    }
}
```




