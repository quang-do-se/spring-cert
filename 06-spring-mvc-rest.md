
### What does REST stand for?

REST stands for REpresentational State Transfer and is an architectural style which allows clients to access and manipulate textual representations of web resources given a set of constraints.

If the application does observe the constraints, certain benefits are gained. Examples of such benefits are *scalability*, *simplicity*, *portability* and *reliability*.

REST supports a lot of different formats, although JSON is the most commonly use. REST can use XML or HTML as well.

REST is an architectural style, thus there's no restrictions in regards on which communication protocal will be used. HTTP is the most commonly used one.

----------

### What is a resource?

Any information that can be named can be a resource: a document or image, a temporal service (e.g. "today's weather in Los Angeles"), a collection of other resources, a non-virtual object (e.g. a person), and so on. In other words, any concept that might be the target of an author's hypertext reference must fit within the definition of a resource. A resource is a conceptual mapping to a set of entities, not the entity that corresponds to the mapping at any particular point in time.

In REST, URIs identify resources.

----------

### Is REST secure? What can you do to secure it?

The REST architectural style in itself does not stipulate any particular security solution but suggests using a layered system style using one or more intermediaries. Security may be a responsibility of one type of intermediary.

This maps very well to what Spring has to offer when it comes to developing REST web services: A REST web service can be developed using Spring without having to consider security at all. Security can later be added to the REST web service using Spring Security, as described in the previous chapter.

While security, such as basic authentication, will make a REST service unavailable to everyone except those who know the login information, the messages passed between clients and the service will still be readable by anyone able to intercept them. To protect the messages in transit, encryption can be used such as with the HTTPS protocol.

----------

### Is REST scalable and/or interoperable?

The short answer is yes, REST web services are both scalable and interoperable.

#### Scalability

The statelessness, the cacheability and the layered system constraints of the REST architectural style allows for scaling a REST web service.Statelessness ensures that requests can be processed by any node in a cluster of services without having to consider server-side state.Cacheability allows for creating responses from cached information without the request having to proceed to the actual service, which improves network efficiency and reduces the load on the service.

A layered system allows for introducing intermediaries such as a load balancer without clients having to modify their behavior as far as sending requests to the service is concerned. The load balancer can then distribute the requests between multiple instances of the service in order to increase the request-processing capacity of the service. 

#### Interoperability

The following elements of the REST architectural style increases interoperability:

- A REST service can support different formats for the resource representation transferred to clients and allow for clients to specify which format it wants to receive data in.
  - Common formats are XML, JSON, HTML which are all formats that facilitate interoperability.
  
- REST resources are commonly identified using URIs, which do not depend on any particular language or implementation.

- The REST architectural style allows for a fixed set of operations on resources.

----------

### Which HTTP methods does REST use?

- HTTP verbs are used as actions to execute on the resources (GET, PUT, PATCH, POST, DELETE, HEAD, and OPTIONS).
- GET is safe and idempotent  (read-only). PUT and DELETE are not safe but generally idempotent.
- POST is neither safe nor idempotent.

----------

### What is an HttpMessageConverter?

The `HttpMessageConverter` interface specifies the properties of a converter that can perform the following conversions:
  - Convert a `HttpInputMessage` to an object of specified type.
  - Convert an object to a `HttpOutputMessage`.
  
There are many implementations of this interface that performs specific conversions. A few examples are:

- `StringHttpMessageConverter`: text/plain

- `AtomFeedHttpMessageConverter`: Converts to/from Atom feeds.

- `ByteArrayHttpMessageConverter`: Converts to/from byte arrays.
- `FormHttpMessageConverter`: Converts to/from HTML forms.

- `Jaxb2RootElementHttpMessageConverter`: Reads classes annotated with the JAXB2 annotations `@XmlRootElement` and `@XmlType` and writes classes annotated with `@XmlRootElement`.

- `MappingJackson2HttpMessageConverter`: Converts to/from JSON using Jackson 2.x.

A `HttpMessageConverter` converts a `HttpInputMessage` created from the request to the parameter type of the controller method that is to process the request. When the controller method has finished, a `HttpMessageConverter` converts the object returned from the controller method to a `HttpOutputMessage`.

----------

### Is `@Controller` a stereotype? Is `@RestController` a stereotype?

A **stereotype annotation** is an annotation that is used to declare the role that a component plays within the application.

> You might think at this point, since `@RestController` is a specialization of `@Controller`, this makes it a stereotype annotation, right? Wrong. First, there are five stereotype annotations in Spring: `@Component`, `@Repository`, `@Service`, `@Indexed` and `@Controller` and they are grouped under the `org. springframework.stereotype` package. All of them, except @Indexed are meta-annotated with a single Spring annotation, which means that the meta-annotated annotation can be treated the same as the annotation it is annotated with. They are part of the stereotype package and have a fundamental role in declaring beans. `@Indexed` is a special case because it was added in Spring 5 to indicate that the annotated element represents a stereotype for the index. This is useful when classpath scanning is replaced with reading a metadata file generated at compile time for identifying bean types. Anyway, `@RestController` is different because it is a composed annotation that is meta-annotated with `@Controller` and `@ResponseBody`. Because of that `@RestController` cannot be treated the same way as `@Controller` and thus it cannot be a stereotype annotation if it cannot be treated the same as one.
>
> --- <cite>Iuliana</cite>

Other sources may disagree:
- https://github.com/spring-projects/spring-framework/wiki/Spring-Annotation-Programming-Model#stereotype-annotations
- https://docs.spring.io/spring-boot/docs/current/reference/html/getting-started.html#getting-started.first-application.code.mvc-annotations

----------

### What is the difference between `@Controller` and `@RestController`?

`@RestController` is different because it is a composed annotation that is meta-annotated with `@Controller` and `@ResponseBody`.

----------

### When do you need to use `@ResponseBody`?

----------

### What are the HTTP status return codes for a successful GET, POST, PUT or DELETE operation?

----------

### When do you need to use `@ResponseStatus`?

----------

### Where do you need to use `@ResponseBody`? What about `@RequestBody`?

----------

### If you saw example Controller code, would you understand what it is doing? Could you tell if it was annotated correctly?

----------

### What Spring Boot starter would you use for a Spring REST application?

----------

### If you saw an example using RestTemplate, would you understand what it is doing?
