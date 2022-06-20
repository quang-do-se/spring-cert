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
