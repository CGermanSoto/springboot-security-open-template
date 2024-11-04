package com.spacecodee.springbootsecurityopentemplate.persistence.entity;

import com.spacecodee.springbootsecurityopentemplate.enums.RoleEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@ToString
@Accessors(chain = true)
@Entity()
@Table(name = "role", schema = "public")
public class RoleEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = -4118972354508428674L;


    private Integer id;
    @Setter
    private RoleEnum name;
    private Set<PermissionEntity> permissionEntities = new HashSet<>();

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_id_gen")
    @SequenceGenerator(name = "role_id_gen", sequenceName = "role_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    public Integer getId() {
        return id;
    }

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "name", nullable = false, length = Integer.MAX_VALUE)
    public RoleEnum getName() {
        return name;
    }

    @OneToMany(mappedBy = "roleEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = PermissionEntity.class)
    public Set<PermissionEntity> getPermissionEntities() {
        return permissionEntities;
    }
}