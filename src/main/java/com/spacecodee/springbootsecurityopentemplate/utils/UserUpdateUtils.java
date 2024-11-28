// UserUpdateUtils.java
package com.spacecodee.springbootsecurityopentemplate.utils;

import com.spacecodee.springbootsecurityopentemplate.data.vo.user.AdminUVO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.user.DeveloperUVO;
import com.spacecodee.springbootsecurityopentemplate.persistence.entity.UserEntity;
import org.jetbrains.annotations.NotNull;

public class UserUpdateUtils {

    private UserUpdateUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static boolean checkForChanges(@NotNull Object userVO, @NotNull UserEntity existingUser) {
        boolean hasChanges = false;

        String username;
        String fullname;
        String lastname;

        if (userVO instanceof AdminUVO adminUVO) {
            username = adminUVO.getUsername();
            fullname = adminUVO.getFullname();
            lastname = adminUVO.getLastname();
        } else if (userVO instanceof DeveloperUVO developerUVO) {
            username = developerUVO.getUsername();
            fullname = developerUVO.getFullname();
            lastname = developerUVO.getLastname();
        } else {
            throw new IllegalArgumentException("Unsupported user type");
        }

        if (!existingUser.getUsername().equals(username)) {
            existingUser.setUsername(username);
            hasChanges = true;
        }

        if (!existingUser.getFullname().equals(fullname)) {
            existingUser.setFullname(fullname);
            hasChanges = true;
        }

        if (!existingUser.getLastname().equals(lastname)) {
            existingUser.setLastname(lastname);
            hasChanges = true;
        }

        return hasChanges;
    }
}