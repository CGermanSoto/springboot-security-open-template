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
@Table(name = "module", schema = "public")
public class ModuleEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = -6383996348361015603L;
    private Integer id;

    private String name;

    private String basePath;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "module_id_gen")
    @SequenceGenerator(name = "module_id_gen", sequenceName = "module_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    public Integer getId() {
        return id;
    }

    @NotNull
    @Column(name = "name", nullable = false, length = Integer.MAX_VALUE)
    public String getName() {
        return name;
    }

    @NotNull
    @Column(name = "base_path", nullable = false, length = Integer.MAX_VALUE)
    public String getBasePath() {
        return basePath;
    }

}