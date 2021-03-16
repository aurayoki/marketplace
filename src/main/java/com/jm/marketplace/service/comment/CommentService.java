package com.jm.marketplace.service.comment;

import com.jm.marketplace.dto.CommentDto;
import com.jm.marketplace.dto.UserDto;
import com.jm.marketplace.dto.goods.AdvertisementDto;
import com.jm.marketplace.model.Advertisement;
import com.jm.marketplace.model.Comment;
import com.jm.marketplace.model.User;

import java.util.List;

public interface CommentService {
    List<Comment> findByUser(User user);
    List<Comment> findByAdvertisement (Advertisement advertisement);
    List<Comment> findAll();
    Comment findById(Long id);
    void saveOrUpdate(Comment comment);
    void deleteById(Long id);
}
