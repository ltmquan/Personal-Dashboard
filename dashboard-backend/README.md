# Dashboard Backend API

Personal dashboard REST API built with Spring Boot and PostgreSQL.

## 🚀 Tech Stack

- **Java 21**
- **Spring Boot 3.3.5**
- **PostgreSQL 16**
- **Maven**
- **Lombok** - Reduces boilerplate code
- **Spring Data JPA** - Database abstraction
- **Hibernate** - ORM implementation

## 📋 Features

- **Task Management** - CRUD operations with optional due dates
- **Event Management** - Calendar events with recurring support
- **Weather Integration** - External API integration (frontend calls directly)
- **RESTful API** - JSON responses with proper HTTP status codes
- **CORS Enabled** - Configured for Vue frontend

## 🛠️ Setup Instructions

### Prerequisites

- Java 21 or higher
- PostgreSQL 16
- Maven 3.6+

### Database Setup

1. **Install PostgreSQL** (via Postgres.app or Homebrew)

2. **Create database and user:**
```sql
CREATE DATABASE dashboard_db;
CREATE USER dashboard_user WITH PASSWORD 'dashboard123';
GRANT ALL PRIVILEGES ON DATABASE dashboard_db TO dashboard_user;
\c dashboard_db
GRANT ALL ON SCHEMA public TO dashboard_user;
```

### Application Setup

1. **Clone the repository**
```bash
git clone <your-repo-url>
cd dashboard/backend
```

2. **Configure database connection**

Edit `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/dashboard_db
spring.datasource.username=dashboard_user
spring.datasource.password=dashboard123
```

3. **Run the application**
```bash
mvn spring-boot:run
```

Application will start at `http://localhost:8080`

4. **Verify it's running**
```bash
curl http://localhost:8080/api/tasks
# Should return: []
```

## 📚 API Documentation

### Interactive API Docs

View interactive API documentation with Swagger UI:
```
http://localhost:8080/swagger-ui.html
```

### API Endpoints

#### Tasks

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/tasks` | Get all tasks |
| GET | `/api/tasks/{id}` | Get task by ID |
| POST | `/api/tasks` | Create new task |
| PUT | `/api/tasks/{id}` | Update task |
| DELETE | `/api/tasks/{id}` | Delete task |
| GET | `/api/tasks/completed` | Get completed tasks |
| GET | `/api/tasks/incomplete` | Get incomplete tasks |
| GET | `/api/tasks/with-deadlines` | Get tasks with due dates |
| GET | `/api/tasks/overdue` | Get overdue tasks |
| GET | `/api/tasks/due-today` | Get tasks due today |

**Example: Create Task**
```bash
POST http://localhost:8080/api/tasks
Content-Type: application/json

{
  "title": "Buy groceries",
  "description": "Milk, eggs, bread",
  "completed": false,
  "dueDate": "2025-10-30T23:59:00"
}
```

**Response:**
```json
{
  "id": 1,
  "title": "Buy groceries",
  "description": "Milk, eggs, bread",
  "completed": false,
  "dueDate": "2025-10-30T23:59:00",
  "createdAt": "2025-10-27T14:30:00",
  "updatedAt": "2025-10-27T14:30:00"
}
```

#### Events

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/events` | Get all events |
| GET | `/api/events/{id}` | Get event by ID |
| POST | `/api/events` | Create new event |
| PUT | `/api/events/{id}` | Update event |
| DELETE | `/api/events/{id}` | Delete event |
| GET | `/api/events/upcoming` | Get upcoming events |
| GET | `/api/events/range?start={start}&end={end}` | Get events in date range |

**Example: Create Event**
```bash
POST http://localhost:8080/api/events
Content-Type: application/json

{
  "title": "CS Class",
  "description": "Data Structures Lecture",
  "startTime": "2025-10-28T14:00:00",
  "endTime": "2025-10-28T15:30:00",
  "location": "Room 301",
  "color": "#4CAF50",
  "isRecurring": true,
  "recurrencePattern": "WEEKLY"
}
```

**Response:**
```json
{
  "id": 1,
  "title": "CS Class",
  "description": "Data Structures Lecture",
  "startTime": "2025-10-28T14:00:00",
  "endTime": "2025-10-28T15:30:00",
  "location": "Room 301",
  "color": "#4CAF50",
  "isRecurring": true,
  "recurrencePattern": "WEEKLY",
  "createdAt": "2025-10-27T14:30:00",
  "updatedAt": "2025-10-27T14:30:00"
}
```

## 🏗️ Project Structure
```
src/main/java/com/quanluu/dashboard/
├── config/              # Configuration classes
│   └── WebConfig.java   # CORS configuration
├── controller/          # REST API endpoints
│   ├── TaskController.java
│   └── EventController.java
├── model/               # Entity classes
│   ├── BaseEntity.java  # Base class with timestamps
│   ├── Task.java
│   └── Event.java
├── repository/          # Database access layer
│   ├── TaskRepository.java
│   └── EventRepository.java
├── service/             # Business logic interfaces
│   ├── TaskService.java
│   ├── EventService.java
│   └── impl/            # Service implementations
│       ├── TaskServiceImpl.java
│       └── EventServiceImpl.java
└── DashboardApplication.java  # Main application class

src/main/resources/
├── application.properties  # Application configuration
└── static/                 # Static resources (if any)
```

## 🗄️ Database Schema

### Tasks Table
```sql
CREATE TABLE tasks (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description VARCHAR(1000),
    completed BOOLEAN NOT NULL DEFAULT false,
    due_date TIMESTAMP,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP
);
```

### Events Table
```sql
CREATE TABLE events (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description VARCHAR(1000),
    start_time TIMESTAMP NOT NULL,
    end_time TIMESTAMP,
    location VARCHAR(200),
    color VARCHAR(7),
    is_recurring BOOLEAN DEFAULT false,
    recurrence_pattern VARCHAR(50),
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP
);
```

## 🔧 Configuration

### CORS Configuration

The API is configured to accept requests from the Vue frontend:
```java
// Allows requests from http://localhost:5173 (Vue dev server)
// Methods: GET, POST, PUT, DELETE
// Configured in: src/main/java/com/quanluu/dashboard/config/WebConfig.java
```

### JPA/Hibernate Configuration
```properties
# Auto-update database schema based on entities
spring.jpa.hibernate.ddl-auto=update

# Show SQL queries in console (helpful for debugging)
spring.jpa.show-sql=true

# PostgreSQL dialect
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
```

## 🧪 Testing

### Manual Testing with cURL

**Get all tasks:**
```bash
curl http://localhost:8080/api/tasks
```

**Create a task:**
```bash
curl -X POST http://localhost:8080/api/tasks \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Test task",
    "description": "Testing API",
    "completed": false
  }'
```

### Testing with Postman

1. Import the API endpoints into Postman
2. Set base URL: `http://localhost:8080`
3. Use provided example requests above

## 🚀 Deployment

### Render.com Deployment (Recommended)

1. Push code to GitHub
2. Create new Web Service on Render
3. Connect GitHub repository
4. Configure:
   - Build Command: `mvn clean package`
   - Start Command: `java -jar target/dashboard-0.0.1-SNAPSHOT.jar`
5. Add PostgreSQL database on Render
6. Set environment variables:
   - `DATABASE_URL` (from Render PostgreSQL)
   - `ALLOWED_ORIGINS` (your frontend URL)

## 🐛 Troubleshooting

### Port Already in Use
```bash
# Find process using port 8080
lsof -i :8080

# Kill the process
kill -9 <PID>
```

### Database Connection Errors
- Verify PostgreSQL is running: Check Postgres.app menu bar icon
- Check database credentials in `application.properties`
- Ensure database `dashboard_db` exists

### CORS Errors
- Verify frontend URL in `WebConfig.java`
- Check browser console for specific CORS error
- Restart Spring Boot after changing CORS config

## 📝 Development Notes

### Adding New Entities

1. Create entity class in `model/` extending `BaseEntity`
2. Create repository interface in `repository/`
3. Create service interface and implementation
4. Create controller with REST endpoints
5. Restart application - Hibernate auto-creates table

### Code Conventions

- **Entities**: Use Lombok annotations (`@Data`, `@NoArgsConstructor`, etc.)
- **Services**: Interface + implementation pattern
- **Controllers**: Use `@RestController` and proper HTTP methods
- **Repositories**: Extend `JpaRepository<Entity, ID>`
- **Timestamps**: Inherited from `BaseEntity`

## 📦 Dependencies

Key dependencies in `pom.xml`:
```xml
<!-- Spring Boot -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>

<!-- Spring Data JPA -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>

<!-- PostgreSQL Driver -->
<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
</dependency>

<!-- Lombok -->
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
</dependency>
```

## 🤝 Contributing

This is a personal project, but suggestions are welcome!

## 📄 License

This project is for personal use.

## 👤 Author

Quan Luu

## 🔗 Related

- [Frontend Repository](../dashboard-frontend) - Vue 3 dashboard interface
- [Deployment Guide](docs/deployment.md) - Coming soon