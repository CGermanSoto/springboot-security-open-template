package com.spacecodee.springbootsecurityopentemplate.data.vo.user.developer;

import com.spacecodee.springbootsecurityopentemplate.data.vo.user.base.BaseUserUpdateVO;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serial;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class DeveloperUVO extends BaseUserUpdateVO {
    @Serial
    private static final long serialVersionUID = 1L;
}