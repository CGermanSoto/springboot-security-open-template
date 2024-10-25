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

@AllArgsConstructor
@NoArgsConstructor
@Setter
@ToString
@Accessors(chain = true)
@Entity(name = "DetailedReportEntity")
@Table(name = "detailed_reports", schema = "public")
public class DetailedReportEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = -6500091939634143060L;
    private Integer id;

    private TicketEntity ticketEntity;

    private String detailedDescription;

    private Instant startTimestamp;

    private Instant endTimestamp;

    private AttentionTypeEntity attentionTypeEntity;

    private ServiceTypeEntity serviceTypeEntity;

    private EquipmentDatumEntity equipmentDatumEntity;

    private PeripheralEntity peripheralEntity;

    private UserEntity adminEntity;

    private UserEntity userEntity;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "detailed_reports_id_gen")
    @SequenceGenerator(name = "detailed_reports_id_gen", sequenceName = "detailed_reports_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    public Integer getId() {
        return id;
    }

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ticket_id", nullable = false)
    public TicketEntity getTicketEntity() {
        return ticketEntity;
    }

    @Column(name = "detailed_description", length = Integer.MAX_VALUE)
    public String getDetailedDescription() {
        return detailedDescription;
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
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "attention_type_id", nullable = false)
    public AttentionTypeEntity getAttentionTypeEntity() {
        return attentionTypeEntity;
    }

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "service_type_id", nullable = false)
    public ServiceTypeEntity getServiceTypeEntity() {
        return serviceTypeEntity;
    }

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "equipment_data_id", nullable = false)
    public EquipmentDatumEntity getEquipmentDatumEntity() {
        return equipmentDatumEntity;
    }

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "peripheral_id", nullable = false)
    public PeripheralEntity getPeripheralEntity() {
        return peripheralEntity;
    }

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "admin_id", nullable = false)
    public UserEntity getAdminEntity() {
        return adminEntity;
    }

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    public UserEntity getUserEntity() {
        return userEntity;
    }

}