
### When do you want to use `@SpringBootTest` annotation?

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

- `JUnit`
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

----------

### When do you want to use `@WebMvcTest`? What does it auto-configure?

----------

### What are the differences between `@MockBean` and `@Mock`?

----------

### When do you want `@DataJpaTest` for? What does it auto-configure?
