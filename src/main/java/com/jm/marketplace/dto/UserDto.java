package com.jm.marketplace.dto;

import com.jm.marketplace.dto.goods.AdvertisementDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private CityDto city;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate date;
    private String phone;
    private String userImg;
    private Set<RoleDto> roles;
    private Set<AdvertisementDto> advertisements;
    private MultipartFile multipartFile;
    private Boolean active;
    private String uniqueCode;
}
