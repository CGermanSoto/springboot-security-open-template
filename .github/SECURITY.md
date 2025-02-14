# Security Policy

## Supported Versions

Use this section to tell people about which versions of your project are currently being supported with security updates.

| Version | Supported          | Notes |
| ------- | ------------------ | ----- |
| 0.1.0   | :white_check_mark: | Current development version |
| < 0.1.0 | :x:               | Pre-release versions |

## Reporting a Vulnerability

We take the security of Spring Security JWT Template seriously. If you believe you have found a security vulnerability, please report it to us as described below.

**Please do NOT report security vulnerabilities through public GitHub issues.**

Instead, please report them via email to:
- Primary: spacecodee@gmail.com
- Subject line: [SECURITY] - Brief description of the issue

You should receive a response within 48 hours. If for some reason you do not, please follow up via email to ensure we received your original message.

Please include the requested information listed below (as much as you can provide) to help us better understand the nature and scope of the possible issue:

* Type of issue
* Full paths of source file(s) related to the manifestation of the issue
* The location of the affected source code (tag/branch/commit or direct URL)
* Any special configuration required to reproduce the issue
* Step-by-step instructions to reproduce the issue
* Proof-of-concept or exploit code (if possible)
* Impact of the issue, including how an attacker might exploit it

## Preferred Languages

We prefer all communications to be in English or Spanish.

## Security Update Process

1. The security team will acknowledge your email within 48 hours
2. We will confirm the issue and determine its severity
3. We will work on a fix and release it as soon as possible, depending on complexity
4. We will notify you when the fix is ready
5. We will credit you (if desired) for finding the vulnerability

## Security Best Practices

When using this template, please ensure:

1. Keep your dependencies up to date
2. Use strong passwords and secure key storage
3. Follow JWT best practices
4. Implement rate limiting in production
5. Use HTTPS in production
6. Configure CORS appropriately
7. Review your security headers

## Security Features

This template includes several security features:

- JWT-based authentication
- Role-based access control
- Permission-based security
- Secure password handling
- Token invalidation mechanisms
- Rate limiting support

## Disclosure Policy

When we receive a security bug report, we will:

- Confirm the problem and determine affected versions
- Audit code to find any similar problems
- Prepare fixes for all supported versions
- Release new security fix versions

## Comments on this Policy

If you have suggestions on how this process could be improved, please submit a pull request.
