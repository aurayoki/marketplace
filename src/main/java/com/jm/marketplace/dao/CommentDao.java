package com.jm.marketplace.dao;

import com.jm.marketplace.model.Advertisement;
import com.jm.marketplace.model.Comment;
import com.jm.marketplace.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentDao extends JpaRepository<Comment, Long> {
    List<Comment> findByUser(User user);
    List<Comment> findByAdvertisement(Advertisement advertisement);
}
