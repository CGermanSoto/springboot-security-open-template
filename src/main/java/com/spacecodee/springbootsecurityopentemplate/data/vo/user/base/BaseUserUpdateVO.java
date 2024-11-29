package com.spacecodee.springbootsecurityopentemplate.data.vo.user.base;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public abstract class BaseUserUpdateVO extends BaseUserVO {
    private int id;
}