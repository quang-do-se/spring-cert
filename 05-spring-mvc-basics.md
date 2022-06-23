
### What is the `@Controller` annotation used for?

The `@Controller` annotation is a specialization of the `@Component` annotation. It is used to annotate classes that implement web controllers, the C in MVC.Such classes need not extend any particular parent class or implement any particular interfaces.

----------

### How is an incoming request mapped to a controller and mapped to a method?

To enable requests to be mapped to one or more methods in a controller class the following steps are taken:

- Enable component scanning.
  - This enables auto-detection of classes annotated with the `@Controller` annotation. In Spring Boot applications, the `@SpringBootApplication` annotation is an annotation that itself is annotated with a number of meta-annotation one of which is the `@ComponentScan` annotation.
  
- Annotate one of the configuration-classes in the application with the `@EnableWebMvc` annotation.
  - In Spring Boot applications, it is sufficient to have one configuration-class in the application implement the WebMvcConfigurer interface.
  
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

----------

### What is `@RequestParam` used for?

----------

### What are the differences between `@RequestParam` and `@PathVariable`?

----------

### What are the ready-to-use argument types you can use in a controller method?

----------

### What are some of the valid return types of a controller method?
