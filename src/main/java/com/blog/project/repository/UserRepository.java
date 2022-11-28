package com.blog.project.repository;

import com.blog.project.model.User;
import org.springframework.data.jpa.repository.JpaRepository;



public interface UserRepository  extends JpaRepository<User, Integer> {


    User findByUsername(String username);


}
