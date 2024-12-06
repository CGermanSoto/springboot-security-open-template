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
security.default.technician.role=#technician role
security.default.customer.role=#customer role
security.cors.allowed-origins=https://example.com,https://www.example.com
security.cors.allowed-methods=GET,POST,PUT,DELETE,OPTIONS
security.cors.allowed-headers=Authorization,Content-Type,Accept-Language
```

📚 Documentation
----------------

- 📖 Swagger UI available at:`/api/v1/swagger-ui/index.html`
- 🔍 OpenAPI docs at:`/api/v1/v3/api-docs`

🛡️ Security Features
---------------------

**User Management**

- ✅ JWT-based authentication
- ✅ Role-based access control
- ✅ Secure password handling
- ✅ Token invalidation on user updates
- ✅ Locale-based responses (en/es)

**Developer Protection**

- ✅ Cannot delete last remaining developer
- ✅ Automatic token invalidation before deletion
- ✅ Username uniqueness validation
- ✅ Safe update operations

**Admin Protection**

- ✅ Cannot delete last remaining admin
- ✅ Automatic token invalidation before deletion
- ✅ Username uniqueness validation
- ✅ Safe update operations

**Customer Management**

- ✅ Full CRUD operations
- ✅ Role-based access control
- ✅ Username uniqueness validation
- ✅ Safe update operations

**Business Rules**

*User Management:*

- System maintains at least one active user per role
- Validates unique usernames across all user types
- Auto-logout (token invalidation) on profile updates
- Internationalization support (i18n)

*Token Security:*

- Automatic invalidation on sensitive data changes
- Proper cleanup during user deletion
- Expiration handling
- Refresh token support
- JWT validation and cleanup

*API Documentation:*

- Swagger UI integration
- OpenAPI 3.0 specification
- Interactive API testing
- Endpoint authorization information

*Useful links:*

- To convert any text into Markdown format: [Markdown Converter](https://euangoddard.github.io/clipboard2markdown/)