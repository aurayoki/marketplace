package com.jm.marketplace.service.comment;

import com.jm.marketplace.config.mapper.MapperFacade;
import com.jm.marketplace.dao.CommentDao;
import com.jm.marketplace.dto.CommentDto;
import com.jm.marketplace.dto.UserDto;
import com.jm.marketplace.dto.goods.AdvertisementDto;
import com.jm.marketplace.exception.CommentNotFoundException;
import com.jm.marketplace.model.Advertisement;
import com.jm.marketplace.model.Comment;
import com.jm.marketplace.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class CommentServiceImpl implements CommentService {

    private final CommentDao commentDao;

    @Autowired
    public CommentServiceImpl(CommentDao commentDao) {
        this.commentDao = commentDao;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Comment> findByUser(User user) {
        log.info("Получение комментов по юзеру. Метод: findByUser");
       // User user = mapperFacade.map(userDto, User.class);
       // return mapperFacade.mapAsList(commentDao.findByUser(user), CommentDto.class);
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
    public List<Comment> findAll() {
        log.info("Получение всех комментов. Метод: findAll");
        return commentDao.findAll();
        //return mapperFacade.mapAsList(commentDao.findAll(), CommentDto.class);
    }

    @Transactional(readOnly = true)
    @Override
    public Comment findById(Long id) {
        log.info("Получение коммента по ID. Метод: findById");
        Comment comment = commentDao.findById(id).orElseThrow(() -> {
            log.info("Ошибка при получении коммента по ID. Метод: findById");
            return new CommentNotFoundException(String.format("Comment not found by id: %s", id));
        });
        return comment;
    }

    @Transactional
    @Override
    public void saveOrUpdate(Comment comment) {
        log.info("CommentService - Сохранение или редктирование коммента. Метод: saveOrUpdate");
        commentDao.save(comment);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        log.info("Удаление коммента по ID. Метод: deleteById");
        commentDao.deleteById(id);
    }
}
