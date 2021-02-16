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
    private final MapperFacade mapperFacade;

    @Autowired
    public CommentServiceImpl(CommentDao commentDao, MapperFacade mapperFacade) {
        this.commentDao = commentDao;
        this.mapperFacade = mapperFacade;
    }

    @Transactional(readOnly = true)
    @Override
    public List<CommentDto> findByUser(UserDto userDto) {
        log.info("Получение комментов по юзеру. Метод: findByUser");
        User user = mapperFacade.map(userDto, User.class);
        return mapperFacade.mapAsList(commentDao.findByUser(user), CommentDto.class);
    }

    @Transactional(readOnly = true)
    @Override
    public List<CommentDto> findByAdvertisement(AdvertisementDto advertisementDto) {
        log.info("Получение комментов по объявлению. Метод: findByAdvertisement");
        Advertisement advertisement = mapperFacade.map(advertisementDto, Advertisement.class);
        return mapperFacade.mapAsList(commentDao.findByAdvertisement(advertisement), CommentDto.class);
    }

    @Transactional(readOnly = true)
    @Override
    public List<CommentDto> findAll() {
        log.info("Получение всех комментов. Метод: findAll");
        return mapperFacade.mapAsList(commentDao.findAll(), CommentDto.class);
    }

    @Transactional(readOnly = true)
    @Override
    public CommentDto findById(Long id) {
        log.info("Получение коммента по ID. Метод: findById");
        Comment comment = commentDao.findById(id).orElseThrow(() ->
                new CommentNotFoundException(String.format("Comment not found by id: %s", id)));
        return mapperFacade.map(comment, CommentDto.class);
    }

    @Transactional
    @Override
    public void saveOrUpdate(CommentDto commentDto) {
        log.info("Сохранение или редктирование коммента. Метод: saveOrUpdate");
        commentDao.save(mapperFacade.map(commentDto, Comment.class));
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        log.info("Удаление коммента по ID. Метод: deleteById");
        commentDao.deleteById(id);
    }
}
