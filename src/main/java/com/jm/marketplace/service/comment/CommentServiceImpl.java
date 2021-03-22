package com.jm.marketplace.service.comment;

import com.jm.marketplace.dao.CommentDao;
import com.jm.marketplace.exception.CommentNotFoundException;
import com.jm.marketplace.model.Advertisement;
import com.jm.marketplace.model.Comment;
import com.jm.marketplace.model.User;
import com.jm.marketplace.service.general.ReadWriteServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CommentServiceImpl extends ReadWriteServiceImpl<Comment, Long> implements CommentService {

    private final CommentDao commentDao;

    @Autowired
    public CommentServiceImpl(CommentDao commentDao) {
        super(commentDao);
        this.commentDao = commentDao;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Comment> findByUser(User user) {
        log.info("Получение комментов по юзеру. Метод: findByUser");
        return commentDao.findByUser(user);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Comment> findByAdvertisement(Advertisement advertisement) {
        log.info("Получение комментов по объявлению. Метод: findByAdvertisement");
        return commentDao.findByAdvertisement(advertisement);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Comment> findById(Long id) {
        log.info("Получение коммента по ID. Метод: findById");
        Comment comment = commentDao.findById(id).orElseThrow(() -> {
            log.info("Ошибка при получении коммента по ID. Метод: findById");
            return new CommentNotFoundException(String.format("Comment not found by id: %s", id));
        });
        return Optional.ofNullable(comment);
    }
}
