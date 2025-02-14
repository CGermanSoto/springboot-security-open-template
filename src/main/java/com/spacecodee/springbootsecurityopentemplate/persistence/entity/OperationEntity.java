package com.spacecodee.springbootsecurityopentemplate.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Accessors(chain = true)
@Entity()
@Table(name = "operation", schema = "public")
public class OperationEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = -6157134909667621423L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "operation_id_gen")
    @SequenceGenerator(name = "operation_id_gen", sequenceName = "operation_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull
    @Column(name = "tag", nullable = false, length = Integer.MAX_VALUE)
    private String tag;

    @NotNull
    @Column(name = "path", nullable = false, length = Integer.MAX_VALUE)
    private String path;

    @NotNull
    @Column(name = "http_method", nullable = false, length = Integer.MAX_VALUE)
    private String httpMethod;

    @NotNull
    @Column(name = "permit_all", nullable = false)
    private Boolean permitAll = false;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "module_id", referencedColumnName = "id", nullable = false)
    private ModuleEntity moduleEntity;

}