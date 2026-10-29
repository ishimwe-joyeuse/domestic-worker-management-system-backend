
# Domestic Worker Management System — Backend

**Student:** ISHIMWE Joyeuse  
**Student ID:** 26756  
**Project:** Domestic Worker Management System

## 1. Project Description

The Domestic Worker Management System is a backend application developed using Java and Spring Boot. It manages information about domestic workers, employers, rooms, skills, and assignments.

The system uses PostgreSQL to store information and provides REST API endpoints that can be tested using Postman.

## 2. Technologies Used

- Java
- Spring Boot
- Spring Data JPA
- Hibernate
- PostgreSQL
- Maven
- Postman
- Git and GitHub

## 4. API Operations — Part A2

| Entity | API path |
|---|---|
| Skills | `/api/skills` |
| Rooms | `/api/rooms` |
| Workers | `/api/workers` |
| Employers | `/api/employers` |
| Assignments | `/api/assignments` |

## 6. Screenshots

### A1: Database Tables and Relationships

**Column table 1**

![Column table 1](screenshots/A1/Screenshot%20table_colum%201-17%20.png)

**Column table 2**

![Column table 2](screenshots/A1/Screenshot%20table%20column%2017-25.png)

#### 2. Foreign Key Table

![Foreign key table](screenshots/A1/Screenshot%20a%20foreign%20key.png)

#### 3. Join Table

![Join table](screenshots/A1/Screenshot%20JoinTable.png)


**Assignment table**

![Assignment table](screenshots/A1/Screenshot%20Assigment.png)

**Employer table**

![Employer table](screenshots/A1/Screenshot%20Employer.png)


**Room table**

![Room table](screenshots/A1/Screenshot%20Room.png)

**Skills table**

![Skills table](screenshots/A1/Screenshot%20Skills.png)

**Worker table**

![Worker table](screenshots/A1/Screenshot%20Worker.png)

### A2: Postman API Testing

**1. Employer POST — Create an Employer**

![Employer POST](screenshots/A2/Employer%20POST.png)

**2. Employer GET — Retrieve an Employer**

![Employer GET](screenshots/A2/Employer%20GET.png)

**3. Employer PUT — Update an Employer**

![Employer PUT](screenshots/A2/Employer%20PUT.png)

**4. Employer DELETE — Delete a Test Employer**

![Employer DELETE](screenshots/A2/Employer%20DELETE.png)

**Employer Not Found**

![Employer Not Found](screenshots/A2/Employer%20Id%20not%20Exit.png)

## 7. Postman Collection

The exported Postman collection is stored in the root directory of this repository. Import the JSON collection into Postman to run the API requests.

## 8. How to Run the Project

1. Install a compatible JDK, Maven, and PostgreSQL.
2. Configure the database connection in `src/main/resources/application.properties`.
3. Open the project folder in VS Code.
4. Run the application in the terminal:

   ```bash
   mvn spring-boot:run
   ```

5. Once the application starts, use Postman to send requests to `http://localhost:8080`.

## 9. Project Status

The project is under development. Refer to the implemented features and Postman tests for the current implementation status.
