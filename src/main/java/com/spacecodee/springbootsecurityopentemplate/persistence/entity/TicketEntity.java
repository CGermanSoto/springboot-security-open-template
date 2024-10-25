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
import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@ToString
@Accessors(chain = true)
@Entity(name = "TicketEntity")
@Table(name = "ticket", schema = "public")
public class TicketEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = -465886445865613658L;
    private Integer id;

    private OfficeEntity officeEntity;

    private RequirementEntity requirementEntity;

    private Instant startTimestamp;

    private Instant endTimestamp;

    private Integer technicianId;

    private UserEntity userEntity;

    private String description;

    private TicketStatusEntity ticketStatusEntity;

    private Set<DetailedReportEntity> detailedReportEntities = new LinkedHashSet<>();

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ticket_id_gen")
    @SequenceGenerator(name = "ticket_id_gen", sequenceName = "ticket_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    public Integer getId() {
        return id;
    }

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "office_id", nullable = false)
    public OfficeEntity getOfficeEntity() {
        return officeEntity;
    }

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "requirement_id", nullable = false)
    public RequirementEntity getRequirementEntity() {
        return requirementEntity;
    }

    @NotNull
    @Column(name = "start_timestamp", nullable = false)
    public Instant getStartTimestamp() {
        return startTimestamp;
    }

    @Column(name = "end_timestamp")
    public Instant getEndTimestamp() {
        return endTimestamp;
    }

    @NotNull
    @Column(name = "technician_id", nullable = false)
    public Integer getTechnicianId() {
        return technicianId;
    }

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    public UserEntity getUserEntity() {
        return userEntity;
    }

    @NotNull
    @Column(name = "description", nullable = false, length = Integer.MAX_VALUE)
    public String getDescription() {
        return description;
    }

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ticket_status_id", nullable = false)
    public TicketStatusEntity getTicketStatusEntity() {
        return ticketStatusEntity;
    }

    @OneToMany(mappedBy = "ticketEntity")
    public Set<DetailedReportEntity> getDetailedReportEntities() {
        return detailedReportEntities;
    }

}