package com.jm.marketplace.controller.rest;

import com.jm.marketplace.dto.CommentDto;
import com.jm.marketplace.dto.goods.AdvertisementDto;
import com.jm.marketplace.filter.CommentFilter;
import com.jm.marketplace.service.comment.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1")
public class CommentRestController {
    private CommentService commentService;
    private CommentFilter commentFilter;

    public CommentRestController(CommentService commentService, CommentFilter commentFilter) {
        this.commentService = commentService;
        this.commentFilter = commentFilter;
    }

    @PutMapping("/comment")
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@RequestBody CommentDto commentDto) {
        commentDto.setComment(commentFilter.filter(commentDto.getComment()));
        commentService.saveOrUpdate(commentDto);
    }

    @PostMapping("/comment/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable long id, @RequestBody CommentDto commentDto) {
        commentDto.setId(id);
        commentService.saveOrUpdate(commentDto);
    }

    @DeleteMapping("/comment/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable long id) {
        commentService.deleteById(id);
    }

    @GetMapping("/comment/{id}")
    public CommentDto getComment(@PathVariable long id) {
        return commentService.findById(id);
    }

    @GetMapping("/comment")
    public List<CommentDto> getAllCommentsByAdvertisement(@RequestBody AdvertisementDto advertisementDto) {
        return commentService.findByAdvertisement(advertisementDto);
    }
}
