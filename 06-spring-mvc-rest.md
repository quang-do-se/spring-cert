<!-- markdown-toc start - Don't edit this section. Run M-x markdown-toc-refresh-toc -->
**Table of Contents**

- [What does REST stand for?](#what-does-rest-stand-for)
- [What is a resource?](#what-is-a-resource)
- [Is REST secure? What can you do to secure it?](#is-rest-secure-what-can-you-do-to-secure-it)
- [Is REST scalable and/or interoperable?](#is-rest-scalable-andor-interoperable)
    - [Scalability](#scalability)
    - [Interoperability](#interoperability)
- [Which HTTP methods does REST use?](#which-http-methods-does-rest-use)
- [What is an HttpMessageConverter?](#what-is-an-httpmessageconverter)
- [Is `@Controller` a stereotype? Is `@RestController` a stereotype?](#is-controller-a-stereotype-is-restcontroller-a-stereotype)
    - [What is a stereotype annotation? What does that mean?](#what-is-a-stereotype-annotation-what-does-that-mean)
- [What is the difference between `@Controller` and `@RestController`?](#what-is-the-difference-between-controller-and-restcontroller)
- [When do you need to use `@ResponseBody`?](#when-do-you-need-to-use-responsebody)
- [What are the HTTP status return codes for a successful GET, POST, PUT or DELETE operation?](#what-are-the-http-status-return-codes-for-a-successful-get-post-put-or-delete-operation)
- [When do you need to use `@ResponseStatus`?](#when-do-you-need-to-use-responsestatus)
- [Where do you need to use `@ResponseBody`? What about `@RequestBody`?](#where-do-you-need-to-use-responsebody-what-about-requestbody)
- [If you saw example Controller code, would you understand what it is doing? Could you tell if it was annotated correctly?](#if-you-saw-example-controller-code-would-you-understand-what-it-is-doing-could-you-tell-if-it-was-annotated-correctly)
- [What Spring Boot starter would you use for a Spring REST application?](#what-spring-boot-starter-would-you-use-for-a-spring-rest-application)
- [If you saw an example using RestTemplate, would you understand what it is doing?](#if-you-saw-an-example-using-resttemplate-would-you-understand-what-it-is-doing)

<!-- markdown-toc end -->

----------

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

- In REST, HTTP verbs are used as actions to execute on the resources.

  - GET
    - Requests a representation of the specified resource. Requests using GET should only be used to request data (they shouldn't include data).
    - idempotent
    - safe
  - PUT
    - Creates a new resource or replaces a representation of the target resource with the request payload.
    - idempotent
    - not-safe
  - PATCH
    - Applies partial modifications to a resource.
    - not-idempotent
    - not-safe
  - POST
    - Sends data to the server, usually leading to changes or resource creation.
    - not-idempotent
    - not-safe
  - DELETE
    - Deletes the specified resource.
    - idempotent
    - not-safe
  - HEAD
    - Requests the headers that would be returned if the HEAD request's URL was instead requested with the HTTP GET method.
    - idempotent
    - safe
  - OPTIONS
    - Requests permitted communication options for a given URL or server.
    - idempotent
    - safe
  
- GET is safe and idempotent  (read-only). PUT and DELETE are not safe but generally idempotent.
- POST is neither safe nor idempotent.

----------

### What is an HttpMessageConverter?

The `HttpMessageConverter` interface specifies the properties of a converter that can perform the following conversions:
  - Convert a `HttpInputMessage` to an object of specified type.
  - Convert an object to a `HttpOutputMessage`.

Message converters are automatically detected and used by Spring in applications configured with `@EnableWebMvc`.

There are many implementations of this interface that performs specific conversions. A few examples are:

- `StringHttpMessageConverter`
  - Data Type: text/plain

- `MappingJackson2HttpMessageConverter`
  - Converts to/from JSON using Jackson 2.x.
  - Data Type: application/*+json

- `AtomFeedHttpMessageConverter`
  - Converts to/from Atom feeds.
  - Data Type: application/atom+xml

- `RssChannelHttpMessageConverter`
  - Data Type: application/rss+xml

- `ByteArrayHttpMessageConverter`
  - Converts to/from byte arrays.

- `FormHttpMessageConverter`
  - Converts to/from HTML forms.

- `Jaxb2RootElementHttpMessageConverter`
  - Reads classes annotated with the JAXB2 annotations `@XmlRootElement` and `@XmlType` and writes classes annotated with `@XmlRootElement`.

- `MappingJackson2XMLHttpMessageConverter`
  - Data Type: application/*+xml

A `HttpMessageConverter` converts a `HttpInputMessage` created from the request to the parameter type of the controller method that is to process the request. When the controller method has finished, a `HttpMessageConverter` converts the object returned from the controller method to a `HttpOutputMessage`.

----------

### Is `@Controller` a stereotype? Is `@RestController` a stereotype?

> You might think at this point, since `@RestController` is a specialization of `@Controller`, this makes it a stereotype annotation, right? Wrong. First, there are five stereotype annotations in Spring: `@Component`, `@Repository`, `@Service`, `@Indexed` and `@Controller` and they are grouped under the `org. springframework.stereotype` package. All of them, except @Indexed are meta-annotated with a single Spring annotation, which means that the meta-annotated annotation can be treated the same as the annotation it is annotated with. They are part of the stereotype package and have a fundamental role in declaring beans. `@Indexed` is a special case because it was added in Spring 5 to indicate that the annotated element represents a stereotype for the index. This is useful when classpath scanning is replaced with reading a metadata file generated at compile time for identifying bean types. Anyway, `@RestController` is different because it is a composed annotation that is meta-annotated with `@Controller` and `@ResponseBody`. Because of that `@RestController` cannot be treated the same way as `@Controller` and thus it cannot be a stereotype annotation if it cannot be treated the same as one.
>
> --- <cite>Iuliana</cite>

Other sources may DISAGREE:
- https://github.com/spring-projects/spring-framework/wiki/Spring-Annotation-Programming-Model#stereotype-annotations
- https://docs.spring.io/spring-boot/docs/current/reference/html/getting-started.html#getting-started.first-application.code.mvc-annotations

#### What is a stereotype annotation? What does that mean?

Stereotype annotations are annotations that are applied to classes that fulfills a certain, distinct, role within an application. The (core) Spring Framework supplies the following stereotype annotations, all found in the `org.springframework.stereotype` package:

- `@Component`
- `@Controller `
- `@Indexed`
- `@Repository`
- `@Service`

Other Spring projects does provide additional stereotype annotations.

----------

### What is the difference between `@Controller` and `@RestController`?

`@RestController` is different because it is a composed annotation that is meta-annotated with `@Controller` and `@ResponseBody`.

When applied at class level, the `@ResponseBody` annotation applies to all the methods in the controller that handles web requests.

----------

### When do you need to use `@ResponseBody`?

The `@ResponseBody` annotation can be applied to either a controller class or a controller handler method. It causes the response to be created from the serialized result of the controller method result processed by a `HttpMessageConverter`. This is useful when you want the web response body to contain the result produced by the controller method, as is the case when implementing a backend service, for example a REST service. The `@ResponseBody` annotation is not needed if the controller class is annotated with `@RestController`.

----------

### What are the HTTP status return codes for a successful GET, POST, PUT or DELETE operation?

HTTP response status codes are three-digit integer codes where the first digit determines the class of the response. There are five different classes of HTTP response codes:

- 1xx
  - Informational. The request has been received and processing of it continues.
  
- 2xx
  - Successful. The request has been successfully received, understood and accepted.
  
- 3xx
  - Redirection. Further action is needed to complete the request.
  
- 4xx
  - Client error. The request is incorrect or cannot be processed.
  
- 5xx
  - Server error. The server failed to process what appears to be a valid request.
  
The following HTTP response status codes are of the successful class:

| HTTP Status | Https Status constant | Oservation                                                                                                                                                                                                                                                |
|-------------|-----------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| 200         | OK                    | Successful GET with returned content                                                                                                                                                                                                                      |
| 201         | CREATED               | Successful PUT or POST (when PUT introduces data that was not recorded previously in the database, PUT can be implemented to behave as a POST), should return a location header that contains the URI of new resource or response containing new resource |
| 204         | NO_CONTENT            | Empty response, after successful PUT or DELETE                                                                                                                                                                                                            |

----------

### When do you need to use `@ResponseStatus`?

The `@ResponseStatus` annotation can also be used to annotate exception classes in order to specify the HTTP response status and reason that are to be returned, instead of the default Server Internal Error (500), when an exception of the type is thrown during the processing of a request in a controller handler method.

In addition, the `@ResponseStatus` annotation can be applied to controller handler methods in order to override the original response status information. In the annotation a HTTP response status code and a reason string can be specified. The `@ResponseStatus` annotation can also be applied at class level in controller classes, in which case it will apply to all the controller handler methods in the class.

**NOTE**: The `reason` properpy is used to specify the reason in case of an error. When setting the "reason" attribute of this annotation, the `HttpServletResponse.sendError` method will be used.

----------

### Where do you need to use `@ResponseBody`? What about `@RequestBody`?

`@ResponseBody` annotation is an annotation that can be applied to controller classes or controller handler methods. It is used when the web response body is to contain the result produced by the controller method(s).

`@RequestBody` annotation can only be applied to parameters of methods. More specific, parameters of controller handler methods. It is used when the web request body is to be bound to a parameter of the controller handler method. That is, the annotated method parameter will contain the web request body when there is a request to be handled by the controller handler method with the annotated parameter. It can be optional by setting attribute `required = false`.

----------

### If you saw example Controller code, would you understand what it is doing? Could you tell if it was annotated correctly?

Maybe.

----------

### What Spring Boot starter would you use for a Spring REST application?

`spring-boot-starter-web`

----------

### If you saw an example using RestTemplate, would you understand what it is doing?

 RestTemplate is thread-safe so it can access any number of services in different parts of an application.

Use `restTemplate.exchange(...)` to test the response status code. It returns `ResponseEntity` object.

`restTemplate.put(...)` and `restTemplate.delete(...)` return void.

`*ForEntity` methods return `ResponseEntity` objects. These contain the response status code as well as the object in the payload.

`*ForObject` methods do not return the HTTP status code.

`ObjectFactory` is NOT used as an argument in any method of RestTemplate.


Supported methods:

- GET
  - `getForObject`: Retrieves a representation via GET.
  - `getForEntity`: Retrieves a `ResponseEntity` (that is, status, headers, and body) by using GET.

- HEAD
  - `headForHeaders`: Retrieves all headers for a resource by using HEAD.

- POST
  - `postForLocation`: Creates a new resource by using POST and returns the Location header from the response.
  - `postForObject`: Creates a new resource by using POST and returns the representation from the response.
  - `postForEntity`: Creates a new resource by using POST and returns the representation from the response.

- PUT
  - `put`: Creates or updates a resource by using PUT.

- PATCH
  - `patchForObject`: Updates a resource by using PATCH and returns the representation from the response. Note that the JDK HttpURLConnection does not support PATCH, but Apache HttpComponents and others do.

- DELETE
  - `delete`: Deletes the resources at the specified URI by using DELETE.

- OPTIONS
  - `optionsForAllow`: Retrieves allowed HTTP methods for a resource by using ALLOW.

- Others (everything can be handled by these)
  - `exchange`: More generalized (and less opinionated) version of the preceding methods that provides extra flexibility when needed. It accepts a `RequestEntity` (including HTTP method, URL, headers, and body as input) and returns a `ResponseEntity`. These methods allow the use of `ParameterizedTypeReference` instead of `Class` to specify a response type with generics.
  - `execute`: The most generalized way to perform a request, with full control over request preparation and response extraction through callback interfaces.
