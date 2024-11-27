// Create new interface IJwtTokenFields.java
package com.spacecodee.springbootsecurityopentemplate.data.base;

import java.util.Date;

public interface IJwtTokenFields {
    String getToken();

    Date getExpiryDate();
}