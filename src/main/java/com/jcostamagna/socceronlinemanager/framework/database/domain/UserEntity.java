package com.jcostamagna.socceronlinemanager.framework.database.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)
@Data
@NoArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotBlank(message = "email must not be blank")
    @Email(message = "Please provide a valid email address")
    @Column(name = "email", nullable = false)
    private String email;

    @JsonIgnore
    @NotBlank(message = "password must not be blank")
    @Column(name = "password", nullable = false)
    private String password;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "team_id")
    private TeamEntity team;

    public UserEntity(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
