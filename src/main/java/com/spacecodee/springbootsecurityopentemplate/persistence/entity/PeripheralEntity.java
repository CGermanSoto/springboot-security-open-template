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
@Entity(name = "PeripheralEntity")
@Table(name = "peripheral", schema = "public")
public class PeripheralEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = -3083462523381516781L;
    private Integer id;

    private String monitor;

    private String keyboard;

    private String mouse;

    private String printer;

    private Set<DetailedReportEntity> detailedReportEntities = new LinkedHashSet<>();

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "peripheral_id_gen")
    @SequenceGenerator(name = "peripheral_id_gen", sequenceName = "peripheral_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    public Integer getId() {
        return id;
    }

    @NotNull
    @Column(name = "monitor", nullable = false, length = Integer.MAX_VALUE)
    public String getMonitor() {
        return monitor;
    }

    @NotNull
    @Column(name = "keyboard", nullable = false, length = Integer.MAX_VALUE)
    public String getKeyboard() {
        return keyboard;
    }

    @NotNull
    @Column(name = "mouse", nullable = false, length = Integer.MAX_VALUE)
    public String getMouse() {
        return mouse;
    }

    @NotNull
    @Column(name = "printer", nullable = false, length = Integer.MAX_VALUE)
    public String getPrinter() {
        return printer;
    }

    @OneToMany(mappedBy = "peripheralEntity")
    public Set<DetailedReportEntity> getDetailedReportEntities() {
        return detailedReportEntities;
    }

}