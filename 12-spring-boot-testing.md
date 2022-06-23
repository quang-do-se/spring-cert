
### When do you want to use `@SpringBootTest` annotation?

When annotating a test class that run Spring Boot based tests, the `@SpringBootTest` annotation provide the following special features as documented in the API documentation of the annotation:

- Uses `SpringBootContextLoader` as the default `ContextLoader`. Provided that no other `ContextLoader` is specified using the `@ContextConfiguration` annotation.

- Searches for a `@SpringBootConfiguration` if no nested `@Configuration` present in the test-class, and no explicit `@Configuration` classes specified in the @SpringBootTest annotation.

- Allows custom `Environment` properties to be defined using the properties attribute of the `@SpringBootTest` annotation.

- Provides support for different web environment modes to create for the test using the **webEnvironment** element of the `@SpringBootTest` annotation.The following web environment modes are available: 
  - **DEFINED_PORT** (creates a web application context without defining a port)
  - **MOCK** (**DEFAULT** if web environment is on your classpath, creates a web application context with a mock servlet environment or a reactive web application context)
  - **NONE** (**DEFAULT** if web environment is NOT on your classpath, creates a regular non-web application context) 
  - **RANDOM_PORT** (creates a web application context and a regular server listening on a random port)

- Registers a `TestRestTemplate` and/or `WebTestClient` bean for use in web tests that are using a fully running web server.
  - `TestRestTemplate` is used for client-side testing (wherever `RestTemplate` is normally used in the code) and supports authentication.
  - `RestTemplate` is not recommended for normal use in test classes.
  - `MockMvc` can be used to mock usage of HTTP endpoints and also has methods for checking the result (server-side testing). It also features a fluent API.
    - The `MockMvcBuilders` class is part of the `spring-test` module and provides a series of builder instances that simulate a call to web specialized beans called controllers. The `standaloneSetup(..)` method returns a builder of type `StandaloneMockMvcBuilder` to register one or more **controller** beans and configure the Spring MVC infrastructure programmatically. It can accept a `WebApplicationContext` as well:

``` java
MockMvc mockMvc = MockMvcBuilders.standaloneSetup(new PersonController(...)).build();
```

``` java
MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

```

----------

### What does `@SpringBootTest` auto-configure?

`@SpringBootTest` auto-configures the following:

- The default context loader.
  - Will be set to `SpringBootContextLoader` if a context loader is not configured in the application.
  
- A `TestRestTemplate` and/or `WebTestClient`. 
  - For use in web tests that are using a fully running web server.
  - A `TestRestTemplate` is only created and configured when `WebEnvironment.RANDOM_PORT` or `WebEnvironment.DEFINED_PORT` is specified in the `@SpringBootTest` annotation.
  
- Configuration as specified by class annotated with `@SpringBootConfiguration` or nested `@Configuration` present in the test-class.
  - Only if no explicit `@Configuration` classes specified in the `@SpringBootTest` annotation.
  
- A `PropertySourcesPlaceholderConfigurer`.

----------

### What dependencies does `spring-boot-starter-test` brings to the classpath?

The `spring-boot-starter-test` starter adds the following test-scoped dependencies to the classpath:

- `JUnit 5`
  - Unit-testing framework.

- Spring Test and Spring Boot Test

- `AssertJ`
  - Fluent assertions for Java.
  
- `Hamcrest`
  - Framework for writing matchers that are both powerful and easy to read.

- `Mockito`
  - Mocking framework for Java.
  
- `JSONassert`
  - Tools for verifying JSON representation of data.
  
- `JsonPath`
  - A Java DSL for reading JSON documents.
  
----------

### How do you perform integration testing with `@SpringBootTest` for a web application?

Four different types of web environments can be specified using the `webEnvironment` attribute of the `@SpringBootTest` annotation:

- `MOCK` **(DEFAULT)**
  - Loads a web `ApplicationContext` and provides a mock web environment. Does not start a web server.
  
- `RANDOM_PORT`
  - Loads a `WebServerApplicationContext`, provides a real web environment and starts an embedded web server listening on a random port. The port allocated can be obtained using the `@LocalServerPort` annotation or `@Value("${local.server.port}")`. Web server runs in a separate thread and server-side transactions will not be rolled back in transactional tests.
  
- `DEFINED_PORT`
  - Loads a `WebServerApplicationContext`, provides a real web environment and starts an embedded web server listening on the port configured in the application properties, or port 8080 if no such configuration exists. Web server runs in a separate thread and server-side transactions will not be rolled back in transactional tests.
  
- `NONE` **(DEFAULT) if web environment is NOT on your classpath**
  - Loads an `ApplicationContext` without providing any web environment.
  
  In the test class, annotated with `@SpringBootTest`, a `TestRestTemplate` and/or `WebTestClient` can be injected and used to send requests either to the mock web environment or to the embedded web server.

----------

### When do you want to use `@WebMvcTest`? What does it auto-configure?

The `@WebMvcTest` annotation is intended to be used in tests which aim are to test only Spring MVC components, disabling full `auto-configuration` and only applying configuration relevant to testing of MVC components. Thus this annotation is not suitable for integration tests.

This annotation is the one to use when a test focuses only on Spring MVC components because it has the effect of **DISABLING** full autoconfiguration and registers configurations only relevant to MVC components.

It will **load only**:

- `@Controller` 
- `@ControllerAdvice`
- `WebMvcConfigurer`
- `Converter`
- `Generic Converter`
- `@JsonComponent`
- `Filter`
- `HandlerInterceptor`
- `HandlerMethodArgumentResolver` components

**`@Component`, `@Service`, `@Repository`, `@ConfigurationProperties` will NOT be scanned when using this annotation.** `@EnableConfigurationProperties` can be used to include `@ConfigurationProperties` beans.

Reference: https://docs.spring.io/spring-boot/docs/2.3.x/reference/htmlsingle/#boot-features-testing-spring-boot-applications-testing-autoconfigured-mvc-tests

The `@WebMvcTest` annotation also auto-configures the following:

- `Spring Security`

- `MockMvc`

- `Caching`

- `Message source`
  - Support for resolving messages typically found in resource bundles, including parameterization and internationalization.

- Groovy templates

- `Gson`
  - A library to create JSON representation from Java objects and vice versa.

- Hypermedia
  - Spring HATEOAS.

- HTTP message converters

- `Jackson`
  - Another library to create JSON representation from Java objects and vice versa.

- `JSON-B `
  - Another library to create JSON representation from Java objects and vice versa.

- `Mustache`
  - Another templating engine.

- `Thymeleaf`
  - Another templating engine.

- `FreeMarker`
  - A templating engine.

- JSR-303 bean validation

- Web MVC

- Web MVC error rendering

- WebClient mock MVC integration

- Selenium WebDriver mock MVC integration

----------

### What are the differences between `@MockBean` and `@Mock`?

Both the `@MockBean` and `@Mock` annotation can be used to create Mockito mocks but there are some differences between the two annotations:

- `@Mock` can only be applied to fields and parameters while `@MockBean` can only be applied to classes and fields.

- `@Mock` can be used to mock any Java class or interface while `@MockBean` only allows for mocking of Spring beans or creation of mock Spring beans.

- `@MockBean` can be used to mock existing beans but also to create new beans that will belong to the Spring application context.

- To be able to use the `@MockBean` annotation, the Spring runner `@RunWith(SpringRunner.class)` or `@ExtendWith(SpringExtension.class)` has to be used to run the associated test.

- `@MockBean` can be used to create custom annotations for specific, reoccurring, needs.

----------

### When do you want `@DataJpaTest` for? What does it auto-configure?

The `@DataJpaTest` annotation is used to annotate test-classes that contain tests of only JPA components (repositories, etc...).

**`@Component`, `@Service`, `@Repository`, `@ConfigurationProperties` will NOT be scanned when using this annotation.** `@EnableConfigurationProperties` can be used to include `@ConfigurationProperties` beans.

Reference: https://docs.spring.io/spring-boot/docs/2.3.x/reference/htmlsingle/#boot-features-testing-spring-boot-applications-testing-autoconfigured-jpa-test

Enable transactions by applying Spring’s `@Transactional` annotation to the test class.

Enable caching on the test class, defaulting to a NoOp cache instance.

Autoconfigure an embedded test database in place of a real one.

Create a `TestEntityManager` bean and add it to the application context.

The `@DataJpaTest` also annotation auto-configures the following:

- Caching

- Spring Data JPA repositories

- A DataSource
  - The data-source will, as default, use an embedded in-memory database (test database).

- Data source transaction manager
  - A transaction manager for a single `DataSource`.

- A `JdbcTemplate`

- `Flyway` database migration tool

- `Liquibase` database migration tool

- JPA base configuration for Hibernate

- Spring transaction

- A test database

- A JPA entity manager for tests

----------

# Extra

### Which annotations can be used in integration tests to test if object serialization and deserialization works as expected?

`@JsonTest`.

Reference: https://docs.spring.io/spring-boot/docs/2.3.x/reference/htmlsingle/#boot-features-testing-spring-boot-applications-testing-autoconfigured-json-tests

----------

### Other Spring Boot tests

- `@RestClientTest`: This annotation will disable full auto-configuration and instead apply only configuration relevant to rest client tests.
