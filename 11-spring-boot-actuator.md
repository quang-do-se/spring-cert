
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


Spring Boot Actuator includes the ability to view and configure the log levels of your application at runtime. You can view either the entire list or an individual logger’s configuration, which is made up of both the explicitly configured logging level as well as the effective logging level given to it by the logging framework. These levels can be one of:

- `TRACE`

- `DEBUG`

- `INFO`

- `WARN`

- `ERROR`

- `FATAL`

- `OFF`

- `null`

`null` indicates that there is no explicit configuration.

To configure a given logger, POST a partial entity to the resource’s URI. 

First, we will get some info about current loggers:

`GET http://localhost:8080/actuator/loggers`: Get a list of all loggers

`GET http://localhost:8080/actuator/loggers/ROOT`: Get the current configuration of ROOT logger. You will see something like this:

``` javascript
{
  "configuredLevel": "INFO",
  "effectiveLevel": "INFO"
}
```

Now, we can update the loggers' configuration with POST request:

`POST http://localhost:8080/actuator/loggers/<logger-id>`
  - `POST http://localhost:8080/actuator/loggers/ROOT`
  - `POST http://localhost:8080/actuator/loggers/com.myapp`

Here is an example data body:
``` javascript
{
  "configuredLevel": "DEBUG"
}
```

----------

### How do you access an endpoint using a tag?

The `/metrics` endpoint is capable of reporting all manner of metrics produced by a running application, including metrics concerning memory, processor, garbage collection, and HTTP requests.

There are so many metrics covered that it would be impossible to monitor them all. You can narrow down the results further by using the tags listed under `availableTags`.

For example, you know that there have been 2,103 requests, but what’s unknown is how many of them resulted in an HTTP 200 versus an HTTP 404 or HTTP 500 response status. Using the `status` tag, you can get metrics for all requests resulting in an HTTP 404 status like this:

``` 
GET localhost:8081/actuator/metrics/http.server.requests?tag=status:404 
```

``` javascript
{
  "name": "http.server.requests",
  "measurements": [
    { "statistic": "COUNT", "value": 31 },
    { "statistic": "TOTAL_TIME", "value": 0.522061212 },
    { "statistic": "MAX", "value": 0 }
  ],
  "availableTags": [
    { "tag": "exception", "values": [ "ResponseStatusException", "none" ] },
    { "tag": "method", "values": [ "GET" ] },
    { "tag": "uri", "values": [ "/actuator/metrics/{requiredMetricName}", "/**" ] }
  ]
}
```


Add any number of `tag=KEY:VALUE` query parameters to the end of the URL to dimensionally drill down on a meter. By specifying the tag name and value with the tag request attribute, you now see metrics specifically for requests that resulted in an HTTP 404 response.

To know how many of those HTTP 404 responses were for the `/**` path? All you need to do to filter this further is to specify the uri tag in the request, like this:

```
GET localhost:8081/actuator/metrics/http.server.requests?tag=status:404&tag=uri:/**
```

``` javascript
{
  "name": "http.server.requests",
  "measurements": [
    { "statistic": "COUNT", "value": 30 },
    { "statistic": "TOTAL_TIME", "value": 0.519791548 },
    { "statistic": "MAX", "value": 0 } ],
  "availableTags": [
    { "tag": "exception", "values": [ "ResponseStatusException" ] },
    { "tag": "method", "values": [ "GET" ] }
  ]
}
```


As you refine the request, the available tags are more limited. The tags offered are only those that match the requests captured by the displayed metrics.

#### Common tags

Common tags are generally used for dimensional drill-down on the operating environment like host, instance, region, stack, etc. Commons tags are applied to all meters and can be configured as shown in the following example.

``` 
management.metrics.tags.region=us-east-1 
management.metrics.tags.stack=prod

```

----------

### What is metrics for?

Metrics are measurements of, in the case of a Spring Boot application, different aspects of the application with the purpose of determining for instance the performance of the application at different points in time under different conditions.

Some examples of types of metrics are:
- Timers
- Gauges
- Counters
- Distribution summaries
- Long task timers

Some examples of metrics that can be found in a Spring Boot application are:
- Response time of HTTP requests.
- Number of active connections in a database connection pool.
- Memory usage (This can be for instance heap memory usage).
- Garbage collection statistics.

----------

### How do you create a custom metric?

The following example shows a component that registers two metrics; the first metric is without tags and the second metric has one single tag.

``` java
package com.myapp;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tags;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MyComponentWithMetrics {
  protected Long mMetricsNumber = 12345L;
  protected List<String> mFruitList = new ArrayList<>();

  public MyComponentWithMetrics(final MeterRegistry inMeterRegistry) {
    mFruitList.add("banana");
    mFruitList.add("guava");
    mFruitList.add("lemon");
    mFruitList.add("orange");

    /* Register a custom metrics without tags that contains a long number. */
    inMeterRegistry.gauge("mycomponent.longnumber",
                          Tags.empty(),
                          mMetricsNumber);

    /* Register a custom metrics with one tag that represents the size of the fruit-list. */
    inMeterRegistry.gaugeCollectionSize("mycomponent.fruitlist.size",
                                        Tags.of("id", "medium"),
                                        mFruitList);
  }
}

```

When viewed in a browser, the first metric, present at the URL `localhost:8080/actuator/metrics/mycomponent.longnumber`, yields the following JSON representation:

``` javascript
{
  "name": "mycomponent.longnumber",
  "description": null,
  "baseUnit": null,
  "measurements": [
    {
      "statistic": "VALUE",
      "value": 12345
    }
  ],
  "availableTags": []
}
```

The second metric, present at the URL `http://localhost:8080/actuator/metrics/mycomponent.fruitlist.size`, has the following JSON representation:

``` javascript
{
  "name": "mycomponent.fruitlist.size",
  "description": null,
  "baseUnit": null,
  "measurements": [
    {
      "statistic": "VALUE",
      "value": 4
    }
  ],
  "availableTags":[
    {
      "tag": "id",
      "values": [
        "medium"
      ]
    }
  ]
}
```


----------

### What is Health Indicator?

A Spring Boot Actuator health indicator provides an indication of health of a certain component or subsystem, such as a database, a message broker etc. A health indicator contains the logic to perform health checks and store the result of the health check in the health indicator, from which it can be retrieved.

If you are running your app, you can go to `http://localhost:8080/actuator/health`

----------

### What are the Health Indicators that are provided out of the box?

| Key           | Name                             | Description                                               |
|---------------|----------------------------------|-----------------------------------------------------------|
| cassandra     | CassandraDriverHealthIndicator   | Checks that a Cassandra database is up.                   |
| couchbase     | CouchbaseHealthIndicator         | Checks that a Couchbase cluster is up.                    |
| db            | DataSourceHealthIndicator        | Checks that a connection to `DataSource` can be obtained. |
| diskspace     | DiskSpaceHealthIndicator         | Checks for low disk space.                                |
| elasticsearch | ElasticsearchRestHealthIndicator | Checks that an Elasticsearch cluster is up.               |
| hazelcast     | HazelcastHealthIndicator         | Checks that a Hazelcast server is up.                     |
| influxdb      | InfluxDbHealthIndicator          | Checks that an InfluxDB server is up.                     |
| jms           | JmsHealthIndicator               | Checks that a JMS broker is up.                           |
| ldap          | LdapHealthIndicator              | Checks that an LDAP server is up.                         |
| mail          | MailHealthIndicator              | Checks that a mail server is up.                          |
| mongo         | MongoHealthIndicator             | Checks that a Mongo database is up.                       |
| neo4j         | Neo4jHealthIndicator             | Checks that a Neo4j database is up.                       |
| ping          | PingHealthIndicator              | Always responds with `UP`.                                |
| rabbit        | RabbitHealthIndicator            | Checks that a Rabbit server is up.                        |
| redis         | RedisHealthIndicator             | Checks that a Redis server is up.                         |
| solr          | SolrHealthIndicator              | Checks that a Solr server is up.                          |


Additional `HealthIndicators` are available but are not enabled by default:

| Key            | Name                          | Description                                             |
|----------------|-------------------------------|---------------------------------------------------------|
| livenessstate  | LivenessStateHealthIndicator  | Exposes the “Liveness” application availability state.  |
| readinessstate | ReadinessStateHealthIndicator | Exposes the “Readiness” application availability state. |


**NOTE**: You can disable them all by setting the `management.health.defaults.enabled` property. 

----------

### What is the Health Indicator status?

Spring Boot Actuator health indicator status expresses the state of a component or subsystem. Health has a status which consists of a code and a description, both stored as strings.

----------

### What are the Health Indicator statuses that are provided out of the box?

There are four predefined health indicator statuses in Spring Boot:

| Status                | Code | Description                                                                  |
|-----------------------|------|------------------------------------------------------------------------------|
| Status.DOWN           | 503  | Component or subsystem is malfunctioning.                                    |
| Status.OUT_OF_SERVICE | 503  | Component or subsystem has been taken out of service and should not be used. |
| Status.UP             | 200  | Component or subsystem is functioning as expected.                           |
| Status.UNKNOWN        | 200  | Status of component or subsystem is not known.                               |

----------

### How do you change the Health Indicator status severity order?

The default severity order for the Health Indicator statuses is **`DOWN, OUT_OF_SERVICE, UP, UNKNOWN`**.

The severity order of the status codes can be changed and new health indicator status codes can be added using the `management.health.status.order` configuration property.

For example, the next snippet declares a status named `FATAL`, and maps it to HTTP status code 501. And since we've added a new status instance, we are customizing the severity order too:

``` yaml
management:
  health:
    status:
      http-mapping:
        FATAL: 501
    order: FATAL, DOWN, OUT_OF_SERVICE, UNKNOWN, UP

```
Alternative for properties file:

```
management.health.status.order=FATAL, DOWN, OUT_OF_SERVICE, UNKNOWN, UP
management.health.status.http-mapping.FATAL=501

```

----------

### Why do you want to leverage 3rd-party external monitoring system?

There are several reasons for using a third-party external monitoring system in combination with Spring Boot Actuator. Some of these reasons are:

- Gather data from multiple applications in one place.
- Retain monitoring data over time. (This gives several subsequent opportunities, some of which are listed below.)
- Allow for querying monitoring data.Allow for visualization of monitoring data.
- Enable alerting based on monitoring data.
- Allow for analysis of monitoring data to find trends and to discover anomalies.
