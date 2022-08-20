# Technologies
- Java 8
- Spring Framework: Core, REST, MVC, ORM, HATEOAS, Security, Test
- Hibernate Framework: ORM, Bean Validation
- Databases: MySQL
- Testing: Spring Test, Mockito, JUnit5
- Security: Stateless, JWT
- Logging: log4j2
- Build tools: Maven
- Server: Tomcat 9.0.55
- VCS: git
- IDE: IntelliJ IDEA Ultimate
- CI/CD: Jenkins
- Others: Jackson, AspectJ, Lombok, Mapstruct
# Installation 
1. Clone this repository.
2. Change `application.properties` file to set corresponding url, port and database credentials.
3. Run `mvn clean install spring-boot:run` to start the application (notice, that injection of environment variable `jwt_secret` is required).
# Database
Database structure is generated automatically, but you must create an empty schema beforehand.
# Endpoints
All get endpoints support pagination
### Authorization
**POST** `/api/v1/users/login` - Get access and refresh tokens  
**POST** `/api/v1/users/register` - Register    
**GET** `/api/v1/users/refresh-token` - Get access and refresh tokens providing a refresh token as a header
### Gift Certificate
**GET** `/api/v1/gift-certificates` - Get list of all certificates  
**GET** `/api/v1/gift-certificates/{id}` - Get certificate by id    
**GET** `/api/v1/gift-certificates/search` - Get certificates by search requirements    
**POST** `/api/v1/gift-certificates` - Add certificate  
**PUT** `/api/v1/gift-certificates` - Update certificate (full DTO required)    
**PATCH** `/api/v1/gift-certificates` - Update certificate (field: newValue list)    
**DELETE** `/api/v1/gift-certificates` - Delete certificate    
### Tag
**GET** `/api/v1/tags` - Get list of all tags     
**GET** `/api/v1/tags/{id}` - Get tag by id     
**GET** `/api/v1/tags/top-tag` - Get the most widely used tag of a user, who has the highest cost of all orders     
**GET** `/api/v1/tags/search` - Get tag by name          
**POST** `/api/v1/tags` - Add tag          
**DELETE** `/api/v1/tags/search` - Delete tag   
### Order
**GET** `/api/v1/orders` - Get list of all orders     
**GET** `/api/v1/orders/{id}` - Get order by id     
**POST** `/api/v1/orders` - Place order   
### User
**GET** `/api/v1/users` - Get list of all users             
**GET** `/api/v1/users/{id}` - Get user by id                   
**GET** `/api/v1/users/search` - Get user by username                   
**GET** `/api/v1/users/{id}/orders` - Get one user's orders                   