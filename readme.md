# 🔐 Spring Security JWT Template - Spring Boot 3.3.5

## 📋 Prerequisites

- ☕ Java 23
- 🐘 PostgreSQL
- 💻 IDE: IntelliJ IDEA, Eclipse, etc.

## ⚙️ Configuration

Create `application-local.properties` in resources folder:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/your_database
spring.datasource.username=your_username
spring.datasource.password=your_password
security.jwt.expiration-in-minutes=#expiration time in minutes
# Make it longer and encode with 'https://www.base64encode.org/'
security.jwt.secret-key=#your secret key
security.default.roles=#admin role
security.default.role=#user role
security.default.developer.role=#developer role
```

Security Features
-----------------

**User Management**

- ✅ JWT-based authentication
- ✅ Role-based access control
- ✅ Secure password handling
- ✅ Token invalidation on user updates

**Developer Protection**

- ❌ Cannot delete last remaining developer
- ✅ Automatic token invalidation before deletion
- ✅ Username uniqueness validation
- ✅ Safe update operations

**Admin Protection**

- ❌ Cannot delete last remaining admin
- ✅ Automatic token invalidation before deletion
- ✅ Username uniqueness validation
- ✅ Safe update operations

**Business Rules**

**Developer Management:**

- System must maintain at least one active developer
- Check and invalidate JWT tokens before developer deletion
- Validate unique usernames across all user types
- Auto-logout (token invalidation) on profile updates

**Token Security:**

- Automatic invalidation on sensitive data changes
- Proper cleanup during user deletion
- Expiration handling
- Refresh token support