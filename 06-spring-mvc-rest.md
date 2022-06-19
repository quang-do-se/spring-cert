### What does REST stand for?


### What is a resource?


### Is REST secure? What can you do to secure it?


### Is REST scalable and/or interoperable?


### Which HTTP methods does REST use?


### What is an HttpMessageConverter?


### Is @Controller a stereotype? Is @RestController a stereotype?

A **stereotype annotation** is an annotation that is used to declare the role that a component plays within the application.

> You might think at this point, since `@RestController` is a specialization of @Controller, this makes it a stereotype annotation, right? Wrong. First, there are five stereotype annotations in Spring: `@Component`, `@Repository`, `@Service`, `@Indexed` and `@Controller` and they are grouped under the `org. springframework.stereotype` package. All of them, except @Indexed are meta-annotated with a single Spring annotation, which means that the meta-annotated annotation can be treated the same as the annotation it is annotated with. They are part of the stereotype package and have a fundamental role in declaring beans. `@Indexed` is a special case because it was added in Spring 5 to indicate that the annotated element represents a stereotype for the index. This is useful when classpath scanning is replaced with reading a metadata file generated at compile time for identifying bean types. Anyway, `@RestController` is different because it is a composed annotation that is meta-annotated with `@Controller` and `@ResponseBody`. Because of that `@RestController` cannot be treated the same way as `@Controller` and thus it cannot be a stereotype annotation if it cannot be treated the same as one.
> --- Iuliana

Other sources may disagree:
- https://github.com/spring-projects/spring-framework/wiki/Spring-Annotation-Programming-Model#stereotype-annotations
- https://docs.spring.io/spring-boot/docs/current/reference/html/getting-started.html#getting-started.first-application.code.mvc-annotations


### What is the difference between @Controller and @RestController?

`@RestController` is different because it is a composed annotation that is meta-annotated with `@Controller` and `@ResponseBody`.


### When do you need to use @ResponseBody?


### What are the HTTP status return codes for a successful GET, POST, PUT or DELETE operation?


### When do you need to use @ResponseStatus?


### Where do you need to use @ResponseBody? What about @RequestBody?


### If you saw example Controller code, would you understand what it is doing? Could you tell if it was annotated correctly?


### What Spring Boot starter would you use for a Spring REST application?


### If you saw an example using RestTemplate, would you understand what it is doing?
