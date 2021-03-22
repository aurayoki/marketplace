package com.jm.marketplace.service.comment;

import com.jm.marketplace.model.Advertisement;
import com.jm.marketplace.model.Comment;
import com.jm.marketplace.model.User;
import com.jm.marketplace.service.general.ReadWriteService;

import java.util.List;

public interface CommentService extends ReadWriteService<Comment, Long> {
    List<Comment> findByUser(User user);
    List<Comment> findByAdvertisement (Advertisement advertisement);
}
