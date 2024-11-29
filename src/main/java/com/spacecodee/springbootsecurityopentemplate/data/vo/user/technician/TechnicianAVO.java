package com.spacecodee.springbootsecurityopentemplate.data.vo.user.technician;

import com.spacecodee.springbootsecurityopentemplate.data.vo.user.base.BaseUserAddVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serial;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class TechnicianAVO extends BaseUserAddVO {
    @Serial
    private static final long serialVersionUID = 1L;
}