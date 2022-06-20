
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

----------

### How is `@ContextConfiguration` used?

----------

### How does Spring Boot simplify writing tests?

----------

### What does `@SpringBootTest` do? How does it interact with `@SpringBootApplication` and `@SpringBootConfiguration`?
