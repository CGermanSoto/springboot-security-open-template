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
@Entity(name = "TicketStatusEntity")
@Table(name = "ticket_status", schema = "public")
public class TicketStatusEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 6224594010770316723L;
    private Integer id;

    private String status;

    private Set<TicketEntity> ticketEntities = new LinkedHashSet<>();

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ticket_status_id_gen")
    @SequenceGenerator(name = "ticket_status_id_gen", sequenceName = "ticket_status_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    public Integer getId() {
        return id;
    }

    @NotNull
    @Column(name = "status", nullable = false, length = Integer.MAX_VALUE)
    public String getStatus() {
        return status;
    }

    @OneToMany(mappedBy = "ticketStatusEntity")
    public Set<TicketEntity> getTicketEntities() {
        return ticketEntities;
    }

}