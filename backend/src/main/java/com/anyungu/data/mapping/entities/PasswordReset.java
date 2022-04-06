package com.anyungu.data.mapping.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity(name = "password_reset")
@Getter
@Setter
@NoArgsConstructor
public class PasswordReset extends BaseEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "reset_token", updatable = false, nullable = false, unique = true)
    private UUID resetToken;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private Boolean active = true;

}
