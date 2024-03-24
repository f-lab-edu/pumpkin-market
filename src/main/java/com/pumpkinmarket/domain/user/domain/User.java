package com.pumpkinmarket.domain.user.domain;

import com.pumpkinmarket.domain.common.domain.AbstractAuditingEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class User extends AbstractAuditingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)
    @Length(min = 1, max = 20)
    private String nickname;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false, unique = true)
    @Email()
    private String email;

    @Column(nullable = false, length = 60)
    @Length(min = 60, max = 60)
    private String password;

    public User(String email, String password, String nickname, String phone) {
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.nickname = nickname;
    }
}
