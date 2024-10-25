package com.spacecodee.springbootsecurityopentemplate.enums;

public enum GlobalStatusEnum {
    ENABLED,
    DISABLED;

    public static GlobalStatusEnum fromString(String value) {
        for (GlobalStatusEnum status : GlobalStatusEnum.values()) {
            if (status.name().equalsIgnoreCase(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("No enum constant " + GlobalStatusEnum.class.getCanonicalName() + "." + value);
    }
}
