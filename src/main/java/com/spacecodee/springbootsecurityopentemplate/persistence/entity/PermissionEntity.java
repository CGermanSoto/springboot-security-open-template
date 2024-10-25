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
@Entity(name = "PermissionEntity")
@Table(name = "permission", schema = "public")
public class PermissionEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = -1389093610467033486L;
    private Integer id;

    private RoleEntity roleEntity;

    private OperationEntity operationEntity;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "permission_id_gen")
    @SequenceGenerator(name = "permission_id_gen", sequenceName = "permission_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    public Integer getId() {
        return id;
    }

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "role_id", nullable = false)
    public RoleEntity getRoleEntity() {
        return roleEntity;
    }

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "operation_id", nullable = false)
    public OperationEntity getOperationEntity() {
        return operationEntity;
    }

}