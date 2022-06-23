
### What is the `@Controller` annotation used for?

The `@Controller` annotation is a specialization of the `@Component` annotation. It is used to annotate classes that implement web controllers, the C in MVC.Such classes need not extend any particular parent class or implement any particular interfaces.

----------

### How is an incoming request mapped to a controller and mapped to a method?

To enable requests to be mapped to one or more methods in a controller class the following steps are taken:

- Create a web application context and register a `DispatcherServlet`.
  - A class implementing the `WebApplicationInitializer` can be used to create a Spring application context. The following classes implement the `WebApplicationInitializer` interface:
    - `AbstractContextLoaderInitializer`: Abstract base class that registers a `ContextLoaderListerer` in the servlet context.
    - `AbstractDispatcherServletInitializer`: Abstract base class that registers a `DispatcherServlet` in the servlet context.
    - `AbstractAnnotationConfigDispatcherServletInitializer`: Abstract base class that registers a `DispatcherServlet` in the servlet context and uses Java-based Spring configuration.
    - `AbstractReactiveWebInitializer`: Creates a Spring application context that uses Java-based Spring configuration. Creates a Spring reactive web application in the servlet container.

- Enable component scanning.
  - This enables auto-detection of classes annotated with the `@Controller` annotation. In Spring Boot applications, the `@SpringBootApplication` annotation is an annotation that itself is annotated with a number of meta-annotation one of which is the `@ComponentScan` annotation.
  
- Annotate one of the configuration-classes in the application with the `@EnableWebMvc` annotation and implement the `WebMvcConfigurer` interface.
  - In Spring Boot applications, it is sufficient to have one configuration-class in the application implement the `WebMvcConfigurer` interface.

- Implement a controller class that is annotated with the `@Controller` annotation.
  - Controller classes can also be annotated with the `@RequestMapping` annotation, in which case it will add a part to the URL which will map to controller method(s).
  - Example: Controller class is annotated with `@RequestMapping("/c2")` and a method in the controller class is annotate with `@RequestMapping("/greeting")`. A request to http://localhost:8080/c2/greeting will be mapped to the to the method in the controller class in the example.
  
- Implement at least one method in the controller class and annotate the method with `@RequestMapping`.
  - Instead of the `@RequestMapping` annotation, one of the specialized annotations can be used. An example is `@GetMapping`. This type of methods are called controller method or handler method.
  
When a request is issued to the application:

- The `DispatcherServlet` of the application receives the request.
- The `DispatcherServlet` maps the request to a method in a controller.
- The `DispatcherServlet` holds a list of classes implementing the `HandlerMapping` interface.
- The `DispatcherServlet` dispatches the request to the controller.
- The method in the controller is executed

----------

### What is the difference between `@RequestMapping` and `@GetMapping`?

The `@RequestMapping` annotation will cause requests using the HTTP method(s) specified in the optional method element of the annotation to be mapped to the annotated controller method.

If no method element specified in the `@RequestMapping` annotation, then requests using any HTTP method will be mapped to the annotated method.

The `@GetMapping` annotation is a specialization of the `@RequestMapping` annotation with `method = RequestMethod.GET`. Thus only requests using the HTTP GET method will be mapped to the method annotated by `@GetMapping`.

----------

### What is `@RequestParam` used for?

The `@RequestParam` annotation is used to annotate parameters to handler methods in order to bind request parameters to method parameters.
Assume there is a controller method with the following signature and annotations:

``` java
@RequestMapping("/greeting")
public String greeting(@RequestParam(name="name", required=false) String inName) {
  //...
}
```

If then a request is sent to the URL http://localhost:8080/greeting?name=Ivan then the inName method parameter will contain the string "Ivan".
The first request parameter in an URL is preceded with a question mark. Subsequent request parameters are preceded with an ampersand. 
Example:

``` java
http://localhost:8080/greeting?firstName=Ivan&amp;lastName=Krizsan
```

----------

### What are the differences between `@RequestParam` and `@PathVariable`?

#### Request Parameters

As in the previous section, the following example shows an URL with two request parameters:

``` java
http://localhost:8080/greeting?firstName=Ivan&amp;lastName=Krizsan
```

The first request parameters has the name firstName and the value Ivan, the second request parameter has the name lastName and the value Krizsan.

#### Path Variables

The following shows an URL from which one could extract two path variables:

``` java
http://localhost:8080/firstname/Ivan/lastname/Krizsan
```

The following handler method signature is a handler method that will map the "Ivan" part of the above URL to a method parameter named `inFirstName` and the "Krizsan" part of the above URL to another method parameter named `inLastName`:

``` java
@RequestMapping("/firstname/{firstName}/lastname/{lastName}")
public String greetWithFirstAndLastName(@PathVariable("firstName") final String inFirstName,
                                        @PathVariable("lastName") final String inLastName) {}
```

Note that:
- The part of the URL in the `@RequestMapping` annotation contains template variables.
- In the above examples these are firstName and lastName and they are surrounded by curly brackets.
- The values in the `@PathVariable` annotations match the name of the template variables in the value of the `@RequestMapping` annotation. This is not necessary if the method parameters have the same names as the template variables.
- In the Spring literature firstName and lastName are also called URI template variables.

#### Difference

The difference between the `@RequestParam` annotation and the `@PathVariable` annotation is that they map different parts of request URLs to handler method arguments.

`@RequestParam` maps query string parameters to handler method arguments.
`@PathVariable` maps a part of the URL to handler method arguments.

----------

### What are the ready-to-use argument types you can use in a controller method?

https://docs.spring.io/spring-framework/docs/5.3.x/reference/html/web.html#mvc-ann-arguments

https://teletype.in/@andrewgolovko/yfTmAy9hxrZ
https://teletype.in/@andrewgolovko/A5gmxjZWJHd

----------

### What are some of the valid return types of a controller method?

https://docs.spring.io/spring-framework/docs/5.3.x/reference/html/web.html#mvc-ann-return-types

https://teletype.in/@andrewgolovko/hoDLxjiuVxf
