package com.jcostamagna.socceronlinemanager.framework.database.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "players")
@EntityListeners(AuditingEntityListener.class)
@Data
@NoArgsConstructor
public class PlayerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotBlank(message = "Player first name must not be blank")
    @Column(name = "first_Name", nullable = false)
    private String firstName;

    @NotBlank(message = "Player last name must not be blank")
    @Column(name = "last_Name", nullable = false)
    private String lastName;

    @NotBlank(message = "Player position must not be blank")
    @Column(name = "position", nullable = false)
    private String position;

    @NotBlank(message = "Player country must not be blank")
    @Column(name = "country", nullable = false)
    private String country;

    @Column(name = "age", nullable = false)
    private int age;

    @Column(name = "value", nullable = false)
    private int value;

    @JsonIgnore
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "team_id")
    private TeamEntity team;

    public PlayerEntity(String firstName, String lastName, String position, String country, int age, int value) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.position = position;
        this.country = country;
        this.age = age;
        this.value = value;
    }
}
