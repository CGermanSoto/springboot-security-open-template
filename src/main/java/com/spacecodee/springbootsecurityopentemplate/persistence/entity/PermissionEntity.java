package com.spacecodee.springbootsecurityopentemplate.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Accessors(chain = true)
@Entity()
@Table(name = "permission", schema = "public")
public class PermissionEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = -1389093610467033486L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "permission_id_gen")
    @SequenceGenerator(name = "permission_id_gen", sequenceName = "permission_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "role_id", insertable = false, updatable = false)
    private Integer roleId;

    @Column(name = "operation_id", insertable = false, updatable = false)
    private Integer operationId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private RoleEntity roleEntity;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "operation_id", nullable = false)
    @ToString.Exclude
    private OperationEntity operationEntity;

}