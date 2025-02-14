package com.spacecodee.springbootsecurityopentemplate.data.dto.core.operation;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class OperationDetailDTO extends OperationDTO {

    private String moduleBasePath;

}