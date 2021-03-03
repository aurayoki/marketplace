package com.jm.marketplace.service.comment;

import com.jm.marketplace.dto.CommentDto;
import com.jm.marketplace.dto.UserDto;
import com.jm.marketplace.dto.goods.AdvertisementDto;

import java.util.List;

public interface CommentService {
    List<CommentDto> findByUser(UserDto userDto);
    List<CommentDto> findByAdvertisement(AdvertisementDto advertisementDto);
    List<CommentDto> findAll();
    CommentDto findById(Long id);
    void saveOrUpdate(CommentDto commentDto);
    void deleteById(Long id);
}
