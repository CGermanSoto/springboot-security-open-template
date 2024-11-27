# Auth Mappers (IAuthRoleMapper, IAuthPermissionMapper)

- Break circular dependencies
- Provide simplified versions of complex objects
- Handle basic auth-related mappings

# Breaking Circular Dependencies
- Before: `RoleMapper` → `PermissionMapper` → `RoleMapper`
- After: `RoleDetailsMapper` → `AuthPermissionMapper`
- After: `AuthRoleMapper` → `PermissionDetailsMapper`