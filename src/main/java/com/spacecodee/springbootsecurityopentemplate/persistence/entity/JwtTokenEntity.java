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
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@ToString
@Accessors(chain = true)
@Entity(name = "JwtTokenEntity")
@Table(name = "jwt_token", schema = "public")
public class JwtTokenEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = -5856445426538329660L;
    private Integer id;

    private String token;

    private Boolean isValid = false;

    private Instant expiryDate;

    private UserEntity userEntity;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "jwt_token_id_gen")
    @SequenceGenerator(name = "jwt_token_id_gen", sequenceName = "jwt_token_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    public Integer getId() {
        return id;
    }

    @NotNull
    @Column(name = "token", nullable = false, length = Integer.MAX_VALUE)
    public String getToken() {
        return token;
    }

    @NotNull
    @Column(name = "is_valid", nullable = false)
    public Boolean getIsValid() {
        return isValid;
    }

    @NotNull
    @Column(name = "expiry_date", nullable = false)
    public Instant getExpiryDate() {
        return expiryDate;
    }

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    public UserEntity getUserEntity() {
        return userEntity;
    }

}