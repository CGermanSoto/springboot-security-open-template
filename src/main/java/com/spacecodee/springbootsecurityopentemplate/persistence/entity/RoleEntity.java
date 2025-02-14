package com.spacecodee.springbootsecurityopentemplate.persistence.entity;

import com.spacecodee.springbootsecurityopentemplate.enums.RoleEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Accessors(chain = true)
@Entity()
@Table(name = "role", schema = "public")
public class RoleEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = -4118972354508428674L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_id_gen")
    @SequenceGenerator(name = "role_id_gen", sequenceName = "role_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "name", nullable = false, length = 20)
    private RoleEnum name;

    @OneToMany(mappedBy = "roleEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = PermissionEntity.class)
    @ToString.Exclude
    private Set<PermissionEntity> permissionEntities = new HashSet<>();

}