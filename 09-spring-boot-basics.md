### What is Spring Boot?

It is a Spring framework, designed to simplify the bootstrapping and development of a new Spring application. 

The framework takes an opinionated approach to configuration, freeing developers from the need to define boilerplate configuration.

Two of the most important parts of Spring Boot are the **starter** and the **auto-configuration** modules.

**Spring Boot starters** are dependency descriptors for different technologies that can be used in Spring Boot applications. For example, if you want to use Apache Artemis for JMS messaging in your Spring Boot application, then you simply add the spring-boot-starter-artemis dependency to your application and rest assured that you have the appropriate dependencies to get you started using Artemis in your Spring Boot application.

**Spring Boot auto-configuration** modules are modules that, like the **Spring Boot starters**, are concerned with a specific technology. The typical contents of an auto-configuration module is:

- One or more `@Configuration` class(es) that creates a set of Spring beans for the technology in question with a default configuration.Typically such a configuration class is conditional and require some class or interface from the technology in question to be on the classpath in order for the beans in the configuration to be created when the Spring context is created.

- A `@ConfigurationProperties` class.Allows for the use of a set of properties related to the technology in question to be used in the application’s properties-file. Properties of a auto-configuration module will have a common prefix, for instance "spring.thymeleaf" for the Thymeleaf module.
  - In the `@ConfigurationProperties` class default values for the different properties may have been assigned as to allow users of the module to get started with a minimum of effort but still be able to do some customization by only setting property values
  - We must add `@ConfigurationPropertiesScan` on top of our `@SpringBootApplication` class or manually register each `@ConfigurationProperties` class with  `@EnableConfigurationProperties(CustomConfig.class)`

----------

### What are the advantages of using Spring Boot?

Some advantages of using Spring Boot are:

- Automatic configuration of "sensible defaults" reducing boilerplate configuration. Configuration adapted to dependencies on the classpath so that, for example, if a HSQLDB dependency is available on the class path, then a data-source bean connecting to an in-memory HSQLDB database is created.

- Enabling getting started quickly developing an application. This is related to the previous step.

- Allows for easy customization when the defaults no longer suffices. Such customization can be accomplished by setting property values in the application’s property file and/or creating Spring beans with the same name as those created by Spring Boot in order to replace the Spring bean definitions.

- A Spring Boot project can produce an executable stand-alone JAR-file. Such a JAR-file can be run from the command line using the regular `java -jar` command and may even contain an embedded web server, for web applications, like Tomcat or Jetty.

- Provides a set of managed dependencies that have been verified to work together.

- Provides a set of managed Maven plug-ins configured to produce certain artifacts.

- Provides non-functional features commonly needed in projects. Some such features are security, externalized configuration, metrics and health-checks.

- Does not generate code.

- Does not require XML configuration.

- Uses well-known, standard, Spring Framework mechanisms. This allows developers familiar with the Spring Framework to quickly learn and use Spring Boot. This also reduce the element of surprise when adopting new technologies that are supported by Spring Boot.

- Popular in the developer community. There are a lot of resources on how to use develop different types of applications using Spring Boot.

- Spring Boot is a mature, well-supported and actively developed product on top of an even more mature, well-supported and actively developed framework.

- Standardize parts of application structure. Developers will recognize common elements when moving between different projects which use Spring Boot.

----------

### What things affect what Spring Boot sets up?

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

### What is a Spring Boot starter? Why is it useful?

The advantages of using `Spring Boot starter` are that all the dependencies needed to get started with a certain technology have been gathered. A developer can rest assured that there are no dependencies missing and that all the dependencies have versions that work well together

----------

### Spring Boot supports both properties and YML files. Would you recognize and understand them if you saw them?

Java properties file:

```
management.endpoints.web.exposure.include: '*'
management.endpoints.web.exposure.exclude: env,beans
```

YAML file:

``` yaml
management:
  endpoints:
    web:
      exposure:
        include: '*'
        exclude: 'env,beans
```

----------

### Can you control logging with Spring Boot? How?

Several aspects of logging can be controlled in different ways in Spring Boot applications.

#### Controlling Log Levels

As per default, messages written with the ERROR, WARN and INFO levels will be output in a Spring Boot application. To enable DEBUG or TRACE logging for the entire application, use the `--debug` or `--trace` flags or set the properties `debug=true` or `trace=true` in the `application.properties` file. 

Log levels can be controlled at a finer granularity using the `application.properties` file.

logging.level.root=WARN
logging.level.com.myapp.spring=DEBUG

The above rows show how log levels can be configured in the `application.properties` file. The first line sets the log level of the root logger to WARN and the second line sets the log level of the `com.myapp.spring` logger to DEBUG.

#### Customizing the Log Pattern

When using the default Logback setup in a Spring Boot application, the log patterns used to write log to the console and to file can be customized using the following properties in the `application.properties` file:

| Property Name           | System Property     | Use                                                              |
|-------------------------|---------------------|------------------------------------------------------------------|
| logging.pattern.console | CONSOLE_LOG_PATTERN | Used to configure the pattern used to output log to the console. |
| logging.pattern.file    | FILE_LOG_PATTERN    | Used to configure the pattern used to output log                 |

#### Choosing a Logging System

Spring Boot uses the **Commons Logging API** for logging. Support for the following underlying logging frameworks is available in Spring Boot:

- **Logback**: This is the default used by Spring Boot.
- **Log4J2**
- **Java Util Logging**: The Java platform’s core logging facilities.

#### Logging System Specific Configuration

Logging-system specific log configuration can be supplied in one of the following files depending on which logging system has been chosen:

- **logback-spring.xml**
  - This logging configuration file allows for Logback-native configuration while also supporting the templating features of Spring Boot. Alternatively the Logback-native configuration can also be supplied in a file named logback.xml but then the Spring Boot templating features will not be available.
  
- **log4j2.xml** 
  - This log configuration file allows for configuring Log4J2 logging using the XML format. It is also possible to configure Log4J2 logging using YAML or JSON and then the name of the configuration files are log4j2.yaml or log4j2.yml and log4j2.json or log4j2.jsn respectively.
  
- **logging.properties** 
  - Configuration file used in conjunction with Java platform’s core logging.

----------

### Where does Spring Boot look for application.properties file by default?

The default properties of a Spring Boot application are stores in the application’s JAR in a file named `application.properties`. When developing, this file is found in the `src/main/resources` directory.

Individual property values defined in this application.properties file can be customized using for example command-line arguments when starting the application. The default properties can be overridden in their entirety using an external `application.properties` file or a YAML equivalent (For example, using `--spring.config.location` or `--spring.config.additional-location`).

Another example is with a profile-specific properties file that contain a subset of the application properties. When the profile is activated, the properties in the profile-specific property file will override the ones in the default properties file (`application.properties`).

----------

### How do you define profile specific property files?

Properties specific to a profile can, as before, be a subset of the properties of the application properties and, for instance, configure the application to run in a certain environment. Such profile specific properties can be defined in properties-files with the following name pattern:

`application-{profile}.properties`

----------

### How do you access the properties defined in the property files?

The `@Value` annotation can be used to inject property values into Spring beans and configuration classes.

In addition, properties can be bound to Java bean class(es) using the `@ConfigurationProperties` annotation on the Java bean class(es) and the `@EnableConfigurationProperties` annotation on one `@Configuration` class. This allows for automatic validation of property values, since the setter methods of such bean classes only accept a certain type. A drawback of this approach is that SpEL expressions in property values are not evaluated.

----------

### What properties do you have to define in order to configure external MySQL?

To connect to an external MySQL database, the following properties need to be set in the `application.properties` file:

``` yaml
spring.datasource.url=jdbc:mysql://localhost/test
spring.datasource.username=username
spring.datasource.password=secret
spring.datasource.driver-class-name=com.mysql.jdbc.Driver
```

----------

### How do you configure default schema and initial data?

#### Configure Default Schema

The name of a default schema can be supplied in the datasource URL, as shown in the following example:

``` 
spring.datasource.url=jdbc:postgresql://localhost/databasename?currentSchema=<default-schema-name>
```

Note that in some databases, MySQL for example, database is equivalent to schema and the default schema is specified by the "databasename" part of the datasource URL in the example above. A datasource URL for MySQL would thus look like this:

``` 
spring.datasource.url=jdbc:mysql://localhost/<default-schema-name>
```

There are other ways of accomplishing the same for specific technologies, such as Hibernate in this example:

```
spring.jpa.properties.hibernate.default_schema=<default-schema-name>
```

However, including the name of the default schema in the datasource URL is to be preferred since it does not create any coupling to the underlying JPA library – Hibernate in this example.

#### Database Initialization

If using JPA, then setting the vendor-independent property `spring.jpa.generate-ddl` to true will cause JPA to initialize the application’s database, creating tables etc.

Using vendor-specific properties, like Hibernate’s `spring.jpa.hibernate.ddl-auto`, may allow for more control over database initialization, depending on the vendor. 

If plain JDBC is used, Spring Boot will execute SQL files named according to the following naming patterns at application startup to initialize the database:

``` 
schema.sql
schema-${platform}.sql
```

Where `platform` is the value of the property `spring.datasource.platform`.

#### Supplying Initial Data

When a Spring Boot application is started, Spring Boot executes SQL files named according to the following naming conventions to allow for insertion of initial data into the database:

```
data.sql
data-${platform}.sql
```

As before, `platform` is the value of the property `spring.datasource.platform`. This method of supplying initial data can be used in projects that uses JPA as well as those who do not.

----------

### What is a fat jar? How is it different from the original jar?

A `fat JAR` or `executable jar` is a self-contained JAR file which contains all dependencies needed for the application to be run. The JAR files of the dependencies are contained in the application’s JAR file. With the current version of Spring Boot, the nested JAR files of the application’s dependencies are contained in the `BOOT-INF/lib` directory.

Reference: https://docs.spring.io/spring-boot/docs/current/reference/html/executable-jar.html

----------

### What embedded containers does Spring Boot support?

Spring Boot supports the following embedded containers:
- Tomcat
- Jetty
- Undertow
