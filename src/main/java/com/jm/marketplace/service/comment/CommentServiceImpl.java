package com.jm.marketplace.service.comment;

import com.jm.marketplace.dao.CommentDao;
import com.jm.marketplace.exception.CommentNotFoundException;
import com.jm.marketplace.model.Advertisement;
import com.jm.marketplace.model.Comment;
import com.jm.marketplace.model.User;
import com.jm.marketplace.service.general.ReadWriteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CommentServiceImpl implements CommentService<Comment, Long> {

    private final CommentDao commentDao;
    private final ReadWriteService<Comment, Long> readWriteService;

    @Autowired
    public CommentServiceImpl(CommentDao commentDao, @Lazy ReadWriteService<Comment, Long> readWriteService) {
        this.commentDao = commentDao;
        this.readWriteService = readWriteService;
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
        return readWriteService.findAll();
        //return mapperFacade.mapAsList(commentDao.findAll(), CommentDto.class);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Comment> findById(Long id) {
        log.info("Получение коммента по ID. Метод: findById");
        Comment comment = readWriteService.findById(id).orElseThrow(() -> {
            log.info("Ошибка при получении коммента по ID. Метод: findById");
            return new CommentNotFoundException(String.format("Comment not found by id: %s", id));
        });
        return Optional.ofNullable(comment);
    }

    @Transactional
    @Override
    public void saveOrUpdate(Comment comment) {
        log.info("CommentService - Сохранение или редктирование коммента. Метод: saveOrUpdate");
        readWriteService.saveOrUpdate(comment);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        log.info("Удаление коммента по ID. Метод: deleteById");
        readWriteService.deleteById(id);
    }
}
