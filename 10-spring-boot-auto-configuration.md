
### How does Spring Boot know what to configure?

Spring Boot detects the dependencies available on the classpath and configures Spring beans accordingly. This is accomplished using a number of annotations that allows for applying conditions to Spring configuration classes or Spring bean declaration methods in such classes.

Examples:

- A Spring bean is to be created only if a certain dependency is available on the classpath. Use `@ConditionalOnClass` and supply a class contained in the dependency in question.

- A Spring bean is to be created only if there is no bean of a certain type or with a certain name created. Use `@ConditionalOnMissingBean` and specify name or type of bean to check.

The following is a list of the condition annotations in Spring Boot and the factors that determine the result of the associated condition:

- `@ConditionalOnClass` - Presence of class on classpath.
- `@ConditionalOnMissingClass` - Absence of class on classpath.
- `@ConditionalOnBean` - Presence of Spring bean or bean type (class).
- `@ConditionalOnMissingBean` - Absence of Spring bean or bean type (class).
- `@ConditionalOnProperty` - Presence of Spring environment property.
- `@ConditionalOnResource` - Presence of resource such as file.
- `@ConditionalOnWebApplication` - If the application is considered to be a web application, that is uses the Spring WebApplicationContext, defines a session scope or has a StandardServletEnvironment.
- `@ConditionalOnNotWebApplication` - If the application is not considered to be a web application.
- `@ConditionalOnExpression` - Bean or configuration active based on the evaluation of a SpEL expression.
- `@ConditionalOnCloudPlatform` - If specified cloud platform, Cloud Foundry, Heroku or SAP, is active.
- `@ConditionalOnEnabledEndpoint` - Specified endpoint is enabled.
- `@ConditionalOnEnabledHealthIndicator` - Named health indicator is enabled.
- `@ConditionalOnEnabledInfoContributor` - Named info contributor is enabled.
- `@ConditionalOnEnabledResourceChain` - Spring resource handling chain is enabled.
- `@ConditionalOnInitializedRestarter` - Spring DevTools RestartInitializer has been applied with non-null URLs.
- `@ConditionalOnJava` - Presence of a JVM of a certain version or within Condition Annotation Condition Factor a version range.
- `@ConditionalOnJndi` - Availability of JNDI InitialContext and specified JNDI locations exist.
- `@ConditionalOnManagementPort` - Spring Boot Actuator management port is either: Different from server port, same as server port or disabled.
- `@ConditionalOnRepositoryType` - Specified type of Spring Data repository has been enabled.
- `@ConditionalOnSingleCandidate` - Spring bean of specified type (class) contained in bean factory and single candidate can be determined.

----------

### What does `@EnableAutoConfiguration` do?

The `@EnableAutoConfiguration` annotation enables Spring Boot auto-configuration. As earlier, Spring Boot auto-configuration attempts to create and configure Spring beans based on the dependencies available on the class-path to allow developers to quickly get started with different technologies in a Spring Boot application and reducing boilerplate code and configuration.

For example:
- If `spring-boot-starter-web` jar is on the classpath, it will bring in all necessarry web dependencies and embedded Tomcat for web application.
- If `spring-boot-starter-data-jpa` jar in on the classpath, it will set up infrastructure beans like transaction manager and entity manager.

----------

### What does `@SpringBootApplication` do?

The `@SpringBootApplication` is a convenience-annotation that can be applied to Spring Java configuration classes. The `@SpringBootApplication` is equivalent to the three annotations:

- `@EnableAutoConfiguration`: enable Spring Boot’s auto-configuration mechanism
- `@ComponentScan`: enable @Component scan on the package where the application is located
- `@SpringBootConfiguration`: enable registration of extra beans in the context or the import of additional configuration classes. An alternative to Spring’s standard `@Configuration` that aids configuration detection in your integration tests.

----------

### Does Spring Boot do component scanning? Where does it look by default?

----------

### How are DataSource and JdbcTemplate auto-configured?

----------

### What is `spring.factories` file for?

----------

### How do you customize Spring Boot auto configuration?

----------

### What are the examples of `@Conditional` annotations? How are they used?
