# TaskBrother
I created this repository to show all my knowledge about spring with java 

## Authentication Endpoints with Code Verification

This project implements a complete authentication system with email verification using verification codes.

### Auth Endpoints

#### POST /auth/register
Register a new user with email verification
```json
{
  "email": "user@example.com",
  "password": "password123"
}
```

#### POST /auth/verify
Verify email with the 6-digit code sent to email
```json
{
  "email": "user@example.com",
  "code": "123456"
}
```

#### POST /auth/login
Login with verified email and password
```json
{
  "email": "user@example.com",
  "password": "password123"
}
```

#### POST /auth/resend-code
Resend verification code to email
```json
{
  "email": "user@example.com"
}
```

### Features

- ✅ User registration with email validation
- ✅ Email verification using 6-digit codes
- ✅ Code expiration (15 minutes)
- ✅ JWT token generation after verification
- ✅ Password encryption using BCrypt
- ✅ Email service with fallback to development logging
- ✅ Comprehensive test coverage
- ✅ H2 database for testing
- ✅ PostgreSQL support for production

### Response Format

All auth endpoints return responses in this format:
```json
{
  "message": "Success/Error message",
  "token": "JWT token (if successful)",
  "email": "user@example.com",
  "success": true/false
}
```
