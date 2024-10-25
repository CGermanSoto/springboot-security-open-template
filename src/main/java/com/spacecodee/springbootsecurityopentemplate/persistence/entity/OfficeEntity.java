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
@Entity(name = "OfficeEntity")
@Table(name = "office", schema = "public")
public class OfficeEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 5424825006909002991L;
    private Integer id;

    private String name;

    private Set<TicketEntity> ticketEntities = new LinkedHashSet<>();

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "office_id_gen")
    @SequenceGenerator(name = "office_id_gen", sequenceName = "office_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    public Integer getId() {
        return id;
    }

    @NotNull
    @Column(name = "name", nullable = false, length = Integer.MAX_VALUE)
    public String getName() {
        return name;
    }

    @OneToMany(mappedBy = "officeEntity")
    public Set<TicketEntity> getTicketEntities() {
        return ticketEntities;
    }

}