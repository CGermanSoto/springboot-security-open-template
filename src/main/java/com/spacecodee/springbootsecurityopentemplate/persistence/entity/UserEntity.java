package com.spacecodee.springbootsecurityopentemplate.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@ToString
@Accessors(chain = true)
@Entity(name = "UserEntity")
@Table(name = "\"user\"", schema = "public")
public class UserEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 4916740038552609450L;
    private Integer id;

    private String username;

    private String password;

    private String fullname;

    private String lastname;

    private RoleEntity roleEntity;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id_gen")
    @SequenceGenerator(name = "user_id_gen", sequenceName = "user_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    public Integer getId() {
        return id;
    }

    @NotNull
    @Column(name = "username", nullable = false, length = Integer.MAX_VALUE)
    public String getUsername() {
        return username;
    }

    @NotNull
    @Column(name = "password", nullable = false, length = Integer.MAX_VALUE)
    public String getPassword() {
        return password;
    }

    @NotNull
    @Column(name = "fullname", nullable = false, length = Integer.MAX_VALUE)
    public String getFullname() {
        return fullname;
    }

    @NotNull
    @Column(name = "lastname", nullable = false, length = Integer.MAX_VALUE)
    public String getLastname() {
        return lastname;
    }

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "rol_id", nullable = false)
    public RoleEntity getRoleEntity() {
        return roleEntity;
    }
}