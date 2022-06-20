
### What type of tests typically use Spring?

Thus Spring is not commonly used in unit tests.

**Integration testing** is testing of several modules of software when they are combined together and tested as a whole. In such tests the relationships between the components is significant. When using the Spring framework for dependency injection, the configuration files or classes used by the dependency injection framework also need to be correct and should therefore be tested. 

Thus integration tests should use Spring dependency injection.

----------

### How can you create a shared application context in a JUnit integration test?

Use `@ContextConfiguration` with `@Configuration`.

----------

### When and where do you use `@Transactional` in testing?

The `@Transactional` annotation can be used in a test that alter some transactional resource, for example a database, that is to be restored to the state it had prior to the test being run. The annotation can be applied at method level, in which case just the annotated test method(s) will run, each in its own transaction. The annotation can also be applied at class level, in which case all the test methods in the class will be executed, each in its own transaction. A transaction in which a test-method is executed will, as default, be rolled back after the test has finished executing.

By default, unit test always rollback with `@Transactional`.

To disable rollback in unit tests, use `@Rollback(false)` or `@Commit`.

`@Transactional` has attributes `rollbackFor`, `rollbackForClassName`, `noRollbackFor`, `noRollbackForClassName` to rollback based on thrown `Exception`.
  - These accepts `Exception` class name or object.

----------

### How are mock frameworks such as `Mockito` or `EasyMock` used?

`Mockito` and `EasyMock` allows for dynamic creation of mock objects that can be used to mock collaborators of class(es) under test that are external to the system or trusted. 

A mock object is similar to a stub, in that it produces predetermined results when methods on the object are invoked. In addition, a mock object can also verify that it was used as expected, for example verifying method invocation sequence, parameters supplied to methods etc.

Mock objects have the advantage over stubs in that they are created dynamically and only for the specific scenario tested. Mock objects are commonly created in a test method or in a test-class before the test methods are executed.

References: 
- https://site.mockito.org/
- https://easymock.org/getting-started.html

----------

### How is `@ContextConfiguration` used?

`@ContextConfiguration` defines class-level metadata that is used to determine how to load and configure an ApplicationContext for *Integration Tests*.


----------

### How does Spring Boot simplify writing tests?

Some of the features offered by Spring Boot that simplify writing tests are:

- Spring Boot has a starter module called `spring-boot-starter-test` which adds the following test-scoped dependencies that can be useful when writing tests:
  - JUnit 
  - Spring Test
  - Spring Boot Test
  - AssertJ
  - Hamcrest
  - Mockito
  - JSONassert
  - JsonPath
  
- Spring Boot provides the `@MockBean` and `@SpyBean` annotations that allow for creation of `Mockito` mock and `Spy` beans and adding them to the Spring application context.

- Spring Boot provide an annotation, `@SpringBootTest`, which allows for running Spring Boot based tests and that provides additional features compared to the Spring TestContext framework.

- Spring Boot provides the `@WebMvcTest` and the corresponding `@WebFluxTest` annotation that enables creating tests that only tests Spring MVC or WebFlux components without loading the entire application context.

- Provides a mock web environment, or an embedded server if so desired, when testing Spring Boot web applications.

- Spring Boot has a starter module named `spring-boot-test-autoconfigure` that includes a number of annotations that for instance enables selecting which auto-configuration classes to load and which not to load when creating the application context for a test, thus avoiding to load all auto-configuration classes for a test.

- Auto-configuration for tests related to several technologies that can be used in Spring Boot applications. Some examples are: JPA, JDBC, MongoDB, Neo4J and Redis.

----------

### What does `@SpringBootTest` do? How does it interact with `@SpringBootApplication` and `@SpringBootConfiguration`?

When annotating a test class that run Spring Boot based tests, the `@SpringBootTest` annotation provide the following special features as documented in the API documentation of the annotation:

- Uses `SpringBootContextLoader` as the default `ContextLoader`. Provided that no other `ContextLoader` is specified using the `@ContextConfiguration` annotation.

- Searches for a `@SpringBootConfiguration` if no nested `@Configuration` present in the test-class, and no explicit `@Configuration` classes specified in the @SpringBootTest annotation.

- Allows custom `Environment` properties to be defined using the properties attribute of the `@SpringBootTest` annotation.

- Provides support for different web environment modes to create for the test using the **webEnvironment** element of the `@SpringBootTest` annotation.The following web environment modes are available: 
  - **DEFINED_PORT** (creates a web application context without defining a port)
  - **MOCK** (creates a web application context with a mock servlet environment or a reactive web application context)
  - **NONE** (creates a regular application context)
  - **RANDOM_PORT** (creates a web application context and a regular server listening on a random port)

- Registers a `TestRestTemplate` and/or `WebTestClient` bean for use in web tests that are using a fully running web server.
  - `TestRestTemplate` is used for client-side testing (wherever `RestTemplate` is normally used in the code) and supports authentication.
  - `RestTemplate` is not recommended for normal use in test classes. MvcTester does not
  - `MockMvc` can be used to mock usage of HTTP endpoints and also has methods for checking the result (server-side testing). It also features a fluent API.

