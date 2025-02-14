package com.spacecodee.springbootsecurityopentemplate.data.dto.base;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public abstract class BaseAuditDTO {

    protected Instant createdAt;

    protected Instant updatedAt;

}