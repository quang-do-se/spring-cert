
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

Spring Boot does not do component scanning unless a configuration class, annotated with `@Configuration`, that is also annotated with the `@ComponentScan` annotation or an annotation, for instance `@SpringBootApplication`, that is annotated with the `@ComponentScan` annotation.

The base package(s) which to scan for components can be specified using the `basePackages` element in the `@ComponentScan` annotation or by specifying one or more classes that are located in the base package(s) to scan for components by using the `basePackageClasses` element. If none of the above elements are used, component scanning will take place using the package in which the configuration class annotated with `@ComponentScan` as the base package.

----------

### How are DataSource and JdbcTemplate auto-configured?

`DataSource` and `JdbcTemplate` are configured by Auto Configuration Classes defined in `spring-boot-autoconfigure` module.

`DataSource` is configured by `DataSourceAutoConfiguration`. `JdbcTemplate` is configured by `JdbcTemplateAutoConfiguration`.
`DataSourceAutoConfiguration` requires some properties to be defined, example below shows MySQL configuration:

``` 
spring.datasource.url=jdbc:mysql://localhost:3306/spring-tutorial
spring.datasource.username=spring-tutorial
spring.datasource.password=spring-tutorial
```

Above properties will be injected into `DataSourceProperties` by the prefix `spring.datasource` and used by `DataSourceAutoConfiguration`.

After having Auto Configuration enabled by default in Spring Boot, configured properties and Database Connector on your classpath, you can just use `@Autowire` to inject `DataSource` or JdbcTemplate.

----------

### What is `spring.factories` file for?

`spring.factories` file, located in `META-INF/spring.factories` location on the classpath, is used by **Auto Configuration** mechanism to locate **Auto Configuration Classes**. Each module that provides Auto Configuration Class needs to have `METAINF/spring.factories` file with `org.springframework.boot.autoconfigure.EnableAutoConfiguration` entry that will point Auto Configuration Classes.

`META-INF/spring.factories` file is consumed by `SpringFactoriesLoader` class, which is used by `AutoConfigurationImportSelector` enabled by `@EnableAutoConfiguration` annotation used by default in `@SpringBootApplication` annotation.

Each Auto Configuration Class lists conditions, in which it should be applied, usually based on the existence of the specific class on the classpath or bean in the context. When conditions are met, `@Configuration` class produced beans within the application context to integrate your application with desired technology.

Auto Configuration use case for `spring.factories` file is probably most popular one, it also allows you to define other entries and achieve context customization with following classes:

- `ApplicationContextInitializer`
- `ApplicationListener`
- `AutoConfigurationImportFilter`
- `AutoConfigurationImportListener`
- `BeanInfoFactory`
- `ContextCustomizer`
- `DefaultTestExecutionListenersPostProcessor`
- `EnableAutoConfiguration`
- `EnvironmentPostProcessor`
- `FailureAnalysisReporter`
- `FailureAnalyzer`
- `ManagementContextConfiguration`
- `PropertySourceLoader`
- `ProxyDetector`
- `RepositoryFactorySupport`
- `SpringApplicationRunListener`
- `SpringBootExceptionReporter`
- `TemplateAvailabilityProvider`
- `TestExecutionListener`


The `spring.factories` file can be used to:

- Register application event listeners regardless of how the Spring Boot application is created (configured).
  - Implement a class that inherits from `SpringApplicationEvent` and register it in the `spring.factories` file.

- Locate auto-configuration candidates in, for instance, your own starter module.

- Register a filter to limit the auto-configuration classes considered. See `AutoConfigurationImportFilter`.

- Activate application listeners that creates a file containing the application process id and/or creates file(s) containing the port number(s) used by the running web server (if any).
  - These listeners, `ApplicationPidFileWriter` and `WebServerPortFileWriter`, both implement the `ApplicationListener` interface.
  
- Register failure analyzers.
  - Failure analyzers implement the `FailureAnalyzer` interface and can be registered in the `spring.factories` file.
  
- Customize the environment or application context prior to the Spring Boot application has started up.
  - Classes that implementing the `ApplicationListener`, `ApplicationContextListener` or the `EnvironmentPostProcessor` interfaces may be registered in the `spring.factories` file.
  
- Register the availability of view template providers. See the `TemplateAvailabilityProvider` interface.

----------

### How do you customize Spring Boot auto configuration?

----------

### What are the examples of `@Conditional` annotations? How are they used?
