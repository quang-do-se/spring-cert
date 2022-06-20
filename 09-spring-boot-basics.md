### What is Spring Boot?

It is a Spring framework, designed to simplify the bootstrapping and development of a new Spring application. 

The framework takes an opinionated approach to configuration, freeing developers from the need to define boilerplate configuration.

Two of the most important parts of Spring Boot are the **starter** and the **auto-configuration** modules.

**Spring Boot starters** are dependency descriptors for different technologies that can be used in Spring Boot applications. For example, if you want to use Apache Artemis for JMS messaging in your Spring Boot application, then you simply add the spring-boot-starter-artemis dependency to your application and rest assured that you have the appropriate dependencies to get you started using Artemis in your Spring Boot application.

**Spring Boot auto-configuration** modules are modules that, like the **Spring Boot starters**, are concerned with a specific technology. The typical contents of an auto-configuration module is:

- One or more `@Configuration` class(es) that creates a set of Spring beans for the technology in question with a default configuration.Typically such a configuration class is conditional and require some class or interface from the technology in question to be on the classpath in order for the beans in the configuration to be created when the Spring context is created.

- A `@ConfigurationProperties` class.Allows for the use of a set of properties related to the technology in question to be used in the application’s properties-file. Properties of a auto-configuration module will have a common prefix, for instance “spring.thymeleaf” for the Thymeleaf module.
In the `@ConfigurationProperties` class default values for the different properties may have been assigned as to allow users of the module to get started with a minimum of effort but still be able to do some customization by only setting property values

----------

### What are the advantages of using Spring Boot?

Some advantages of using Spring Boot are:

- Automatic configuration of “sensible defaults” reducing boilerplate configuration. Configuration adapted to dependencies on the classpath so that, for example, if a HSQLDB dependency is available on the class path, then a data-source bean connecting to an in-memory HSQLDB database is created.

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

----------

### What is a Spring Boot starter? Why is it useful?

----------

### Spring Boot supports both properties and YML files. Would you recognize and understand them if you saw them?

----------

### Can you control logging with Spring Boot? How?

----------

### Where does Spring Boot look for application.properties file by default?

----------

### How do you define profile specific property files?

----------

### How do you access the properties defined in the property files?

----------

### What properties do you have to define in order to configure external MySQL?

----------

### How do you configure default schema and initial data?

----------

### What is a fat jar? How is it different from the original jar?

----------

### What embedded containers does Spring Boot support?
