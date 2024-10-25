package com.spacecodee.springbootsecurityopentemplate.persistence.entity;

import jakarta.persistence.*;
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
@Entity(name = "EquipmentDatumEntity")
@Table(name = "equipment_data", schema = "public")
public class EquipmentDatumEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 7358367770331957251L;
    private Integer id;

    private String cpu;

    private String ram;

    private String storage;

    private Set<DetailedReportEntity> detailedReportEntities = new LinkedHashSet<>();

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "equipment_data_id_gen")
    @SequenceGenerator(name = "equipment_data_id_gen", sequenceName = "equipment_data_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    public Integer getId() {
        return id;
    }

    @Column(name = "cpu", length = Integer.MAX_VALUE)
    public String getCpu() {
        return cpu;
    }

    @Column(name = "ram", length = Integer.MAX_VALUE)
    public String getRam() {
        return ram;
    }

    @Column(name = "storage", length = Integer.MAX_VALUE)
    public String getStorage() {
        return storage;
    }

    @OneToMany(mappedBy = "equipmentDatumEntity")
    public Set<DetailedReportEntity> getDetailedReportEntities() {
        return detailedReportEntities;
    }

}