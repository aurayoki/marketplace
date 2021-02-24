package com.jm.marketplace.password.reset.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@Table(name = "password_reset_token")
public class PasswordResetToken {

//    PasswordResetToken класс используется для восстановления пароля,
//    когда пароль сброшен будет создан объект этого класса и специальная ссылка содержащая этот токен будет отпарвлена пользователю
//    по email срок действия ссылки ограничен 24 часами

    private static final int EXPIRATION = 60 * 24;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private String token;

    private String email;

    private Date expiryDate;

    public PasswordResetToken(String token, String email) {
        super();
        this.token = token;
        this.email = email;
        this.expiryDate = calculateExpiryDate(EXPIRATION);
    }

    //метод который вычисляет время когда истекает срок действия, в дальнейшем используется для ссылки email
    private Date calculateExpiryDate(final int expiryTimeInMinutes) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());
        calendar.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(calendar.getTime().getTime());
    }
}
