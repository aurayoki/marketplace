package com.jm.marketplace.dao;

import com.jm.marketplace.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface UserDao extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByPhone(String phone);

    @Query(value = "FROM User AS u WHERE DAY(u.date) = DAY(:date) AND MONTH(u.date) = MONTH(:date)")
    List<User> findUserByBirthday(@Param(value = "date") LocalDate date);

    @Query(value = "FROM User AS u WHERE u.uniqueCode = :uniqueCode")
    Optional<User> findUserByUniqueCode(@Param(value = "uniqueCode") String uniqueCode);
}
