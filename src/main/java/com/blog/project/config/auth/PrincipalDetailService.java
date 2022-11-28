package com.blog.project.config.auth;


import com.blog.project.model.User;
import com.blog.project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service //Bean 등록
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
