package com.jm.marketplace.dto;

import com.jm.marketplace.dto.goods.AdvertisementDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {

    @Setter
    private Long id;
    @Setter
    private String comment;
    @Setter
    private AdvertisementDto advertisement;
    @Setter
    private UserDto user;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CommentDto commentDto = (CommentDto) o;

        if (!Objects.equals(id, commentDto.id)) return false;
        return Objects.equals(comment, commentDto.comment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.comment);
    }
}
