package com.blog.project.repository;

import com.blog.project.model.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ReplyReposiotry extends JpaRepository<Reply,Integer> {

    @Modifying
    @Query(value = "INSERT INTO Reply(userId,boardId,content,createDate) VALUES(?1,?2,?3,now())",nativeQuery = true)
    int mSave(int userId,int boardId,String content);

}
