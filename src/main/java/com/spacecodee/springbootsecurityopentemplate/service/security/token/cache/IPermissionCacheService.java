package com.spacecodee.springbootsecurityopentemplate.service.security.token.cache;

import java.util.List;

public interface IPermissionCacheService {

    void cachePermissions(String roleId, List<String> permissions);

    List<String> getPermissionsFromCache(String roleId);

    void removeFromCache(String roleId);

    void clearCache();

}
