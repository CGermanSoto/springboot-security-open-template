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
@Entity()
@Table(name = "operation", schema = "public")
public class OperationEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = -6157134909667621423L;
    private Integer id;

    private String tag;

    private String path;

    private String httpMethod;

    private Boolean permitAll = false;

    private ModuleEntity moduleEntity;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "operation_id_gen")
    @SequenceGenerator(name = "operation_id_gen", sequenceName = "operation_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    public Integer getId() {
        return id;
    }

    @NotNull
    @Column(name = "tag", nullable = false, length = Integer.MAX_VALUE)
    public String getTag() {
        return tag;
    }

    @NotNull
    @Column(name = "path", nullable = false, length = Integer.MAX_VALUE)
    public String getPath() {
        return path;
    }

    @NotNull
    @Column(name = "http_method", nullable = false, length = Integer.MAX_VALUE)
    public String getHttpMethod() {
        return httpMethod;
    }

    @NotNull
    @Column(name = "permit_all", nullable = false)
    public Boolean getPermitAll() {
        return permitAll;
    }

    @NotNull
    @ManyToOne
    @JoinColumn(name = "module_id", referencedColumnName = "id", nullable = false)
    public ModuleEntity getModuleEntity() {
        return moduleEntity;
    }
}