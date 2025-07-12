# Todo List Application with Spring Boot

A complete Todo list application built with Spring Boot, featuring JWT authentication, CRUD operations, and a RESTful API.

## Features

- ✅ User authentication with JWT tokens
- ✅ User registration and login
- ✅ CRUD operations for todos
- ✅ User-specific todo management
- ✅ Input validation and error handling
- ✅ MySQL database integration
- ✅ RESTful API design
- ✅ Global exception handling

## Technology Stack

- **Backend**: Spring Boot 3.5.3
- **Database**: MySQL
- **Authentication**: JWT (JSON Web Tokens)
- **Security**: Spring Security
- **ORM**: Spring Data JPA with Hibernate
- **Build Tool**: Maven
- **Java Version**: 24

## Prerequisites

- Java 24 or higher
- MySQL 8.0 or higher
- Maven 3.6 or higher

## Setup Instructions

### 1. Database Setup

1. Create a MySQL database:
```sql
CREATE DATABASE todo_db;
```

2. Update database credentials in `src/main/resources/application.properties`:
```properties
spring.datasource.username=your_username
spring.datasource.password=your_password
```

### 2. Application Setup

1. Clone or download the project
2. Navigate to the project directory
3. Run the application:
```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## API Documentation

### Authentication Endpoints

#### Register User
```
POST /api/auth/register
Content-Type: application/json

{
    "username": "john_doe",
    "password": "password123"
}
```

**Response:**
```json
{
    "success": true,
    "message": "User registered successfully",
    "data": {
        "token": null,
        "message": "User registered successfully",
        "username": "john_doe"
    }
}
```

#### Login User
```
POST /api/auth/login
Content-Type: application/json

{
    "username": "john_doe",
    "password": "password123"
}
```

**Response:**
```json
{
    "success": true,
    "message": "Login successful",
    "data": {
        "token": "eyJhbGciOiJIUzUxMiJ9...",
        "message": "Login successful",
        "username": "john_doe"
    }
}
```

### Todo Endpoints

**Note:** All todo endpoints require authentication. Include the JWT token in the Authorization header:
```
Authorization: Bearer <your_jwt_token>
```

#### Get All Todos
```
GET /api/todos
Authorization: Bearer <jwt_token>
```

#### Get Todo by ID
```
GET /api/todos/{id}
Authorization: Bearer <jwt_token>
```

#### Create Todo
```
POST /api/todos
Content-Type: application/json
Authorization: Bearer <jwt_token>

{
    "title": "Complete project",
    "description": "Finish the Spring Boot todo application",
    "completed": false
}
```

#### Update Todo
```
PUT /api/todos/{id}
Content-Type: application/json
Authorization: Bearer <jwt_token>

{
    "title": "Updated title",
    "description": "Updated description",
    "completed": true
}
```

#### Delete Todo
```
DELETE /api/todos/{id}
Authorization: Bearer <jwt_token>
```

## Project Structure

```
src/main/java/com/example/todo/
├── config/                 # Configuration classes
├── controller/             # REST controllers
│   ├── AuthController.java
│   └── TodoController.java
├── dto/                   # Data Transfer Objects
│   ├── ApiResponse.java
│   ├── AuthRequest.java
│   ├── AuthResponse.java
│   └── TodoRequest.java
├── exception/             # Exception handling
│   └── GlobalExceptionHandler.java
├── model/                 # Entity classes
│   ├── Todo.java
│   └── User.java
├── repository/            # Data access layer
│   ├── TodoRepository.java
│   └── UserRepository.java
├── security/              # Security configuration
│   ├── JwtAuthenticationFilter.java
│   ├── JwtUtil.java
│   ├── SecurityConfig.java
│   └── UserDetailsServiceImpl.java
└── TodoApplication.java   # Main application class
```

## Database Schema

### Users Table
- `id` (Primary Key)
- `username` (Unique)
- `password` (Encrypted)

### Todos Table
- `id` (Primary Key)
- `title` (Required, max 100 chars)
- `description` (Optional, max 500 chars)
- `completed` (Boolean)
- `user_id` (Foreign Key to Users)

## Security Features

- **JWT Authentication**: Stateless authentication using JSON Web Tokens
- **Password Encryption**: BCrypt password hashing
- **Input Validation**: Bean validation for all inputs
- **Access Control**: Users can only access their own todos
- **CORS Configuration**: Configured for web client access

## Error Handling

The application includes comprehensive error handling:

- **Validation Errors**: Detailed field-level validation messages
- **Authentication Errors**: Clear unauthorized access messages
- **Authorization Errors**: Access denied for unauthorized operations
- **Runtime Errors**: Graceful handling of unexpected errors

## Testing the API

### Using cURL

1. **Register a user:**
```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"username":"testuser","password":"password123"}'
```

2. **Login:**
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"testuser","password":"password123"}'
```

3. **Create a todo (replace TOKEN with the JWT from login):**
```bash
curl -X POST http://localhost:8080/api/todos \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer TOKEN" \
  -d '{"title":"Test Todo","description":"This is a test todo","completed":false}'
```

4. **Get all todos:**
```bash
curl -X GET http://localhost:8080/api/todos \
  -H "Authorization: Bearer TOKEN"
```

## Configuration

Key configuration options in `application.properties`:

- `jwt.secret`: Secret key for JWT signing
- `jwt.expiration`: JWT token expiration time (milliseconds)
- `spring.jpa.hibernate.ddl-auto`: Database schema generation strategy
- `spring.datasource.*`: Database connection properties

## Troubleshooting

1. **Database Connection Issues**: Ensure MySQL is running and credentials are correct
2. **JWT Token Issues**: Check that the token is valid and not expired
3. **Port Conflicts**: Change `server.port` in application.properties if 8080 is in use

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests if applicable
5. Submit a pull request

## License

This project is licensed under the MIT License. 