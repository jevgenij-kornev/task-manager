Dear Developer,

I am pleased to present to you the Task Manager project — a RESTful application for task management, developed using modern technologies and best practices. 
Below you will find a description of the technologies used and their application in the project.

Technologies and Tools:

	1.	Java:
	•	The main programming language used for developing the application.
	2.	Spring Boot:
	•	Used to create a production-ready application with minimal configuration.
	•	Annotations: @RestController, @Autowired, @Service, @Repository.
	3.	Spring Data JPA:
	•	For working with databases through repositories.
	•	Annotations: @Entity, @Id, @GeneratedValue.
	4.	JUnit 5:
	•	Used for unit and integration testing.
	•	Annotations: @Test, @BeforeEach, @ExtendWith.
	5.	Mockito:
	•	For creating mock objects and simulating dependency behavior.
	•	Methods: Mockito.when(), Mockito.any(), Mockito.verify().
	6.	Spring Security:
	•	To ensure application security.
	•	Annotations: @EnableWebSecurity, @Configuration.
	7.	Docker:
	•	For containerizing the application, which simplifies deployment and testing.
	•	Commands: docker build, docker run, docker ps, docker logs.
	8.	H2 Database:
	•	An in-memory database used for development and testing.
	•	Configuration via application.properties.
	9.	Postman:
	•	A tool for testing REST APIs, verifying requests and responses.
	10.	Maven:
	•	A build and dependency management system.
	•	Commands: mvn clean install, mvn test.
	11.	IntelliJ IDEA:
	•	The development environment (IDE) supporting all the used technologies.
	12.	Git:
	•	Version control system for managing source code.
	•	Commands: git add, git commit, git push, .gitignore.
	13.	SLF4J and Logback:
	•	For logging information about application operations.
	•	Configuration via logback-spring.xml, using the @Slf4j annotation.

Functionality:

	1.	Users:
	•	CRUD operations for users, including creation, reading, updating, and deletion.
	2.	Projects:
	•	CRUD operations for projects with the ability to link tasks to projects.
	3.	Tasks:
	•	CRUD operations for tasks with support for completion status.

Logging:

To ensure observability and simplify debugging, logging is implemented at all levels, from controllers to services and repositories. 
Logs are recorded to files for later analysis.

Containerization:

The project is containerized using Docker, simplifying deployment and testing in different environments. 
You can build and run the application container with the following commands:

	•	docker build -t taskmanager .
 	•	docker run -p 8080:8080 taskmanager

I am confident that using these technologies and practices will make working with the project enjoyable and productive. 
If you have any questions or suggestions for improving the project, I am always happy to hear your feedback.

Best regards,
Jevgenij Kornev.
