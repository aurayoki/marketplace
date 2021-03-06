package com.jm.marketplace.model;

import com.jm.marketplace.dto.RoleDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "users")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "first_name", length = 30)
    private String firstName;

    @Column(name = "last_name", length = 30)
    private String lastName;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email", unique = true)
    private String email;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "phone", unique = true)
    private String phone;

    @Column(name = "user_img")
    private String userImg;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    @OneToMany(mappedBy="user")
    private Set<Advertisement> advertisements;

    @Column(name = "active")
    private Boolean active;

    @Column(name = "unique_code")
    private String uniqueCode;

    public User(String firstName, String lastName, String password, String email, City city, LocalDate date, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.email = email;
        this.city = city;
        this.date = date;
        this.phone = phone;
    }

    public User(String firstName, String lastName, String password, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.email = email;
    }

    public String getRolesString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Role role : roles) {
            stringBuilder.append(role.getName()).append(" ");
        }
        return stringBuilder.toString();
    }
}
