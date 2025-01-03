# FFA Backend Spring Service

## Overview
This project implements a robust REST API backend service for the Family Film App (FFA), a sophisticated recommendation system that helps groups decide on movies and TV shows to watch together. The service is built with Spring Boot and incorporates advanced features including authentication, recommendation algorithms, and real-time data processing.

## Key Features
- **Dual Authentication System**
  - JWT token-based authentication
  - Firebase integration for secure user management
  - Role-based access control

- **Advanced Recommendation Engine**
  - Collaborative filtering algorithms
  - Group preference analysis
  - Real-time recommendation updates
  - Content-based filtering

- **RESTful API Architecture**
  - Scalable endpoint design
  - Comprehensive API documentation
  - Request validation and error handling
  - Rate limiting and security measures

## Technical Architecture

### Core Components
1. **Authentication Layer**
   - JWT token generation and validation
   - Firebase Auth integration
   - Session management
   - Security configurations

2. **Service Layer**
   - Business logic implementation
   - Recommendation algorithm processing
   - Data transformation and validation
   - Cache management

3. **Data Access Layer**
   - Repository implementations
   - Database configurations
   - Entity relationships
   - Query optimizations

4. **API Layer**
   - REST controller implementations
   - Request/Response DTOs
   - Validation handlers
   - Exception management

### Database Schema
- User profiles and preferences
- Movie and TV show metadata
- Watch history and ratings
- Group configurations
- Recommendation cache

## Technology Stack
- Spring Boot 2.7+
- Spring Security
- Spring Data JPA
- PostgreSQL
- Firebase Admin SDK
- Docker
- Maven
- Redis Cache
- Kubernetes
- CI/CD
- Jenkins

## API Documentation
The API is deployed at: `[https://ffa-develop-back.onrender.com/api/](http://23.88.43.3:32371/webjars/swagger-ui/index.html#/user-controller/getAllUsers)`

## Development Setup

### Prerequisites
- JDK 11+
- Maven 3.6+
- PostgreSQL 13+
- Docker (Instructions bellow)

### Local Development
1. Clone the repository:
```bash
git clone [https://github.com/apptolast/FFABackSpringGod.git](https://github.com/apptolast/FFABackSpringGod.git)
cd FFABackSpringGod
```

2. Configure environment variables:
```bash
cp .env.example .env
# Edit .env with your configurations

# You have to do it the application.properties

## The file have this parameters :

spring.application.name=back
spring.datasource.url=jdbc:postgresql://postgres-service.ffa-dev.svc.cluster.local:5432/ffa
spring.datasource.username=YOUR_DATABASE_USERNAME
spring.datasource.password=YOUR_DATABASE_PASSWORD
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.show-sql=true
springdoc.api-docs.path=/api-docs
springdoc.swagger-ui.path=/swagger-ui.html
management.endpoints.web.exposure.include=health,info,metrics,httptrace
management.endpoint.health.show-details=always
spring.data.redis.host=redis-service
spring.data.redis.port=6379
spring.redis.lettuce.pool.max-active=8
spring.redis.lettuce.pool.max-idle=8
spring.redis.lettuce.pool.min-idle=0
spring.redis.lettuce.pool.max-wait=5000
spring.redis.timeout=5000
logging.file.name=/app/logs/spring-boot.log
logging.file.path=/app/logs
logging.pattern.file=%d{yyyy-MM-dd HH:mm:ss.SSS} %-5level [%thread] %logger{36} - %msg%n
logging.level.org.springframework.web=DEBUG
logging.level.org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration=DEBUG
firebase.url=https://identitytoolkit.googleapis.com/v1/accounts:signInWithCustomToken
firebase.api-key=API_KEY_FOR_YOUR_FIREBASE_PROJECT
firebase.api-filename=YOUR_CLIENT_JSON_OF_GOOGLE_CLOUD_CONSOLE
firebase.pass=YOUR_PASSWORD
tmdb.api-key=TMDB_API_KEY
tmdb.api-url-movies=https://api.themoviedb.org/3/movie/
tmdb.api-url-series=https://api.themoviedb.org/3/tv/
tmdb.api-url-search=https://api.themoviedb.org/3/search
tmdb.language=en-US
tmdb.default-page=1
logging.level.root=INFO
logging.level.com.ffa.back=DEBUG
```

### Docker Deployment
```bash
docker-compose up -d
```

## Testing
The project includes comprehensive test coverage:

```bash
# Run unit tests
mvn test

# Run integration tests
mvn verify
```

## Performance Considerations
- Connection pooling optimization
- Caching strategies implementation
- Query optimization
- Batch processing for bulk operations

## Security Measures
- CORS configuration
- XSS protection
- SQL injection prevention
- Rate limiting
- Input validation

## Monitoring and Logging
- Actuator endpoints for monitoring
- Structured logging with SLF4J
- Performance metrics collection
- Error tracking and reporting

## Contributing
1. Fork the repository
2. Create your feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## License
This project is licensed under the MIT License - see the LICENSE file for details.

## Acknowledgments
- Spring Boot team for the excellent framework
- Firebase team for Authentication services
- The open-source community for various dependencies
