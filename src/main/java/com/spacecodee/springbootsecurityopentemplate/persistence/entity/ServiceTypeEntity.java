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
import java.util.LinkedHashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@ToString
@Accessors(chain = true)
@Entity(name = "ServiceTypeEntity")
@Table(name = "service_type", schema = "public")
public class ServiceTypeEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1960543876754783546L;
    private Integer id;

    private String name;

    private Set<DetailedReportEntity> detailedReportEntities = new LinkedHashSet<>();

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "service_type_id_gen")
    @SequenceGenerator(name = "service_type_id_gen", sequenceName = "service_type_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    public Integer getId() {
        return id;
    }

    @NotNull
    @Column(name = "name", nullable = false, length = Integer.MAX_VALUE)
    public String getName() {
        return name;
    }

    @OneToMany(mappedBy = "serviceTypeEntity")
    public Set<DetailedReportEntity> getDetailedReportEntities() {
        return detailedReportEntities;
    }

}