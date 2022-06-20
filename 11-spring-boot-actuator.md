
### What value does Spring Boot Actuator provide?

Spring Boot Actuator provides the following features which can be automatically applied to Spring Boot applications:

- Application health monitoring
- Application metrics
- Management
- Auditing


With a Spring Boot application in which Spring Boot Actuator is enabled and with the following two properties set to the values shown, the available Actuator endpoints can be listed by sending a HTTP GET request, for example in a browser, to the http://localhost:8080/actuator/ URL.

``` 
management.endpoints.enabled-by-default=true
management.endpoints.web.exposure.include=*
```

----------

### What are the two protocols you can use to access actuator endpoints?

Spring Boot Actuator endpoints can be accessed over HTTP and JMX, provided that an endpoint is enabled. An endpoint also need to be accessible over the protocol in question - some endpoints, like the jolokia endpoint, are not accessible over a certain protocol (JMX in the case of the jolokia endpoint)

By default most endpoints are available over JMX protocal (except web application endpoints: `heapdump`, `jolokia`, `logfile`, `prometheus`).

To change which endpoints are exposed, use the following technology-specific include and exclude properties:

| Property                                  | Default |
|-------------------------------------------|---------|
| management.endpoints.jmx.exposure.exclude |         |
| management.endpoints.jmx.exposure.include | *       |
| management.endpoints.web.exposure.exclude |         |
| management.endpoints.web.exposure.include | health  |

----------

### What are the actuator endpoints that are provided out of the box?

The following technology-agnostic built-in Spring boot Actuator endpoints are available:

| ID               | Description                                                                                                                                                   |
|------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------|
| auditevents      | Exposes audit events information for the current application. Requires an `AuditEventRepository` bean.                                                        |
| beans            | Displays a complete list of all the Spring beans in your application.                                                                                         |
| caches           | Exposes available caches.                                                                                                                                     |
| conditions       | Shows the conditions that were evaluated on configuration and auto-configuration classes and the reasons why they did or did not match.                       |
| configprops      | Displays a collated list of all `@ConfigurationProperties`.                                                                                                   |
| env              | Exposes properties from Spring’s `ConfigurableEnvironment`.                                                                                                   |
| flyway           | Shows any Flyway database migrations that have been applied. Requires one or more `Flyway` beans.                                                             |
| health           | Shows application health information.                                                                                                                         |
| httptrace        | Displays HTTP trace information (by default, the last 100 HTTP request-response exchanges). Requires an `HttpTraceRepository` bean.                           |
| info             | Displays arbitrary application info.                                                                                                                          |
| integrationgraph | Shows the Spring Integration graph. Requires a dependency on `spring-integration-core`.                                                                       |
| loggers          | Shows and modifies the configuration of loggers in the application.                                                                                           |
| liquibase        | Shows any Liquibase database migrations that have been applied. Requires one or more `Liquibase` beans.                                                       |
| metrics          | Shows “metrics” information for the current application.                                                                                                      |
| mappings         | Displays a collated list of all `@RequestMapping` paths.                                                                                                      |
| quartz           | Shows information about Quartz Scheduler jobs.                                                                                                                |
| scheduledtasks   | Displays the scheduled tasks in your application.                                                                                                             |
| sessions         | Allows retrieval and deletion of user sessions from a Spring Session-backed session store. Requires a servlet-based web application that uses Spring Session. |
| shutdown         | Lets the application be gracefully shutdown. **DISABLED BY DEFAULT**.                                                                                         |
| startup          | Shows the startup steps data collected by the `ApplicationStartup`. Requires the `SpringApplication` to be configured with a `BufferingApplicationStartup`.   |
| threaddump       | Performs a thread dump.                                                                                                                                       |



If your application is a web application (Spring MVC, Spring WebFlux, or Jersey), you can use the following additional endpoints:

| ID         | Description                                                                                                                                                                                                |
|------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| heapdump   | Returns a heap dump file. On a HotSpot JVM, an `HPROF`-format file is returned. On an OpenJ9 JVM, a `PHD`-format file is returned.                                                                         |
| jolokia    | Exposes JMX beans over HTTP when Jolokia is on the classpath (not available for WebFlux). Requires a dependency on `jolokia-core`.                                                                         |
| logfile    | Returns the contents of the logfile (if the `logging.file.name` or the `logging.file.path` property has been set). Supports the use of the HTTP `Range` header to retrieve part of the log file’s content. |
| prometheus | Exposes metrics in a format that can be scraped by a Prometheus server. Requires a dependency on `micrometer-registry-prometheus`.                                                                         |


----------

### What is info endpoint for? How do you supply data?

When the `env` contributor is enabled, you can customize the data exposed by the `info` endpoint by setting `info.*` Spring properties. All `Environment` properties under the info key are automatically exposed.

``` yaml
info:
  app:
    name: My Application
    description: This is an example of Spring Actuator module
    version: 1.0-SNAPSHOT
```

----------

### How do you change logging level of a package using loggers endpoint?

----------

### How do you access an endpoint using a tag?

----------

### What is metrics for?

----------

### How do you create a custom metric?

----------

### What is Health Indicator?

----------

### What are the Health Indicators that are provided out of the box?

----------

### What is the Health Indicator status?

----------

### What are the Health Indicator statuses that are provided out of the box?

----------

### How do you change the Health Indicator status severity order?

----------

### Why do you want to leverage 3rd-party external monitoring system?
