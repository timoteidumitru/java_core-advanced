# Spring Boot Lessons

### Base Annotations
- **@SpringBootApplication** - *Combines several other annotations from Spring Boot, simplifying the configuration of the application.*
- **@Component** - *Marks a class as a Spring bean, allowing it to be scanned and managed by the Spring IoC container.*
- **@Configuration** - *Defines a class that provides bean definitions for the Spring IoC container, serving as a configuration source.*

###

### Stereotype Annotations (used to create beans automatically in the application context)
- **@Component** - *A generic, reusable, and configurable bean used for dependency injection within the Spring framework.*
- **@RestController / @Controller** - *Manages web endpoints and handles HTTP requests, acting as the API interface in an MVC pattern.*
- **@Service** - *Handles business logic, often stateless, and sits in the service layer to manage backend operations.*
- **@Repository** - *Responsible for data access and persistence, performing CRUD operations and interacting with the database.*

### 

### Spring Core Annotations
- **@Configuration** - *Indicates that the class is a source of bean definitions, acting as a configuration for the Spring IoC container.*
- **@Bean** - *Defines a method that produces a bean to be managed by the Spring container, providing an instance of a class.*
- **@Autowired** - *Automatically injects dependencies into a bean, enabling loose coupling and easier configuration.*
- **@Qualifier** - *Used alongside @Autowired to specify which bean should be injected when multiple candidates are available.*
- **@Lazy** - *Delays the initialization of a bean until it is needed, improving application startup time by loading beans on demand.*
- **@Value** - *Injects values from properties or environment variables into a bean's field, method, or constructor, allowing externalized configuration.*
- **@PropertySource** - *Specifies the location of a properties file to be loaded into the Spring environment, providing access to configuration values.*
- **@ConfigurationProperties** - *Binds the properties from a configuration file to a bean, allowing structured access to configuration data.*
- **@Profile** - *Used to specify that a bean or configuration should only be activated under certain environments or profiles.*
- **@Scope** - *Defines the lifecycle and visibility of a bean, such as singleton, prototype, request, or session scope.*

### Microservices with Spring Boot
- Service ***Registration*** and ***Discovery*** using ***Eureka*** - allow microservices to register themselves and discover other services.
- ***Spring Cloud Config*** - allow externalization of configuration properties and retrieve them from a configuration server.
- ***Load Balancing and Resilience*** - help distributing incoming requests and provides fault tolerance by automatically rerouting requests to a healthy instance.
- ***Circuit Breaker Pattern*** - managing faults and failures in distributed systems by providing fallback mechanisms.
- ***API Gateway*** - **Spring Cloud Gateway** act as entry point for incoming requests and can preform routing, filtering and load balancing.
- ***Monitoring and Metrics*** - **Spring Boot Actuator** provides production ready endpoints for monitoring and managing microservices.


