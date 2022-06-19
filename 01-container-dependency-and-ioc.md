
### What is Dependency Injection?

Dependency injection (DI) is a process whereby objects define their dependencies (that is, the other objects with which they work) only through constructor arguments, arguments to a factory method, or properties that are set on the object instance after it is constructed or returned from a factory method. **The container then injects those dependencies when it creates the bean.** This process is fundamentally the inverse (hence the name, Inversion of Control) of the bean itself controlling the instantiation or location of its dependencies on its own by using direct construction of classes or the Service Locator pattern.

Code is cleaner with the DI principle, and decoupling is more effective when objects are provided with their dependencies. The object does not look up its dependencies and does not know the location or class of the dependencies. As a result, your classes become easier to test, particularly when the dependencies are on interfaces or abstract base classes, which allow for stub or mock implementations to be used in unit tests.

https://docs.spring.io/spring-framework/docs/current/reference/html/core.html##beans-factory-collaborators

Dependency injection means giving an object its instance variables.

Dependency injection is basically providing the objects that an object needs (its dependencies) instead of having it construct them itself.
It's a very useful technique for testing, since it allows dependencies to be mocked or stubbed out.

`Inversion of Control (IoC)` is a common characteristic of frameworks
that facilitate injection of dependencies. And the basic idea of the dependency injection
pattern is to have a separate object that injects dependencies with the required behavior,
based on an interface contract.


#### Advantages of Dependency Injection

- Increase cohesion

- Decrease coupling between classes and their dependencies

- Reduce boilerplate code

- Increase testability (with mocked objects)

- Make program more reusable, maintainable

- Increase flexibility (by interfaces)

- Better design of applications when using dependency injection

- Standardize parts of application development


#### Disadvantage of Dependency Injection

- More classes and interfaces

----------

### What is an interface and what are the advantages of making use of them in Java?

#### What is an interface?
Interfaces form a contract between the class and the outside world, and this contract is enforced at build time by the compiler. If your class claims to implement an interface, all methods defined by that interface must appear in its source code before the class will successfully compile.

An interface is an abstract type that can contain the following:
- Constants
- Method signatures (These are methods that have no implementation)
- Default methods (A method with an implementation that, if not implemented in a class that implements the interface, will be used aas a default implementation of the method in question. This can be useful when adding new methods(s) to an interface and not watnting to modify all the classes that implement the interface)
- Static methods (Static method with implementation)
- Nested types (Such a nested type can be an enumeration)

#### What are the advantages of making use of them in Java?

- Allow for decoupling of a contract and its implementation(s).
  - The contract is the interface and the implementation are the classes that implement the interface. This allows for implementations to be easily interchanged. Interfaces can be defined separately from the implementations.
- Allow For modularization of Java programs.
- Allow for handling of groups of object in a similar fashion.
- Increase testability.
  - Using interface types when referencing other objects make it easy to replace such references with `mock` and `stub` objects that implement the same interface(s).


----------

### What is an `ApplicationContext`?

An application context is an instance of any type implementing `org.springframework.context.ApplicationContext`, which is the central interface for providing configuration for a Spring application. The application context will manage all objects instantiated and initialized by the Spring IoC container (Spring beans).

It is responsible for:
- Instantiating beans in the application context.
- Configuring the beans in the application context.
- Assembling the beans in the application context.
- Managing the life-cycle of Sping beans.


Given the interfaces the `ApplicationContext` interface inherits from, an application context have the following properties:
- Is a bean factory
  - A bean factory instantiates, configures and assembles Spring beans. Configuration and assembly is value and dependency injection. A bean factory also manages the beans.
- Is a hierarchical bean factory.
- Is a resource loader that can load file resources in a generic fashion.
- Is an event publisher. As such it publishes application events to listener in the application.
- Is a message source. Can resolve messages and supports internationalization.
- Is an environment.
  - From such an environment, properties can be resolved. The environment also allows maintaining named groups of beans, so-called `profiles`. The beans beloging to a certain `profile` are registered with the application context only when the `profile` is active.


There can be more than one application context in a single Spring application. Multiple application contexts can be arranged in a parent child hierarchy where the relation is directional from child context to parent context. Many child contexts can have one and the same parent context. Some commonly used implementations of the ApplicationContext interface are:
  - `AnnotationConfigApplicationContext`: Standalone application context used with configuration in the form of annotated classes.
  - `AnnotationConfigWebApplicationContext`: Same as `AnnotationConfigApplicationContext` but for web applications.
  - `ClassPathXmlApplicationContext`: Standalone application context used with XML configuration located on the classpath of the application.
  - `FileSystemXmlApplicationContext`: Standalone application context used with XML configuration located as one ore more files in the file system.
  - `XmlWebApplicationContext`: Web application context used with XML configuration.


`DispatcherServlet` has 2 `WebApplicationContext`: Servlet Web Application Context (DispatcherServletContext) and Root Application Context
  - Servlet Web Application Context inherits all the beans already defined in the Root Application Context.
  - Root Application Context contains all non-web beans (services, datasources, repositories...) and is instantiated using a bean of type `org.springframework.web.context.ContextLoaderListener`.
  - https://docs.spring.io/spring-framework/docs/current/reference/html/web.html#mvc-servlet-context-hierarchy

<p align="center">
  <img src="img/dispatcher-servlet.png" alt="Dispatcher Servlet Context" width="50%"/>
</p>


----------

### How are you going to create a new instance of an `ApplicationContext`?

#### Non-Web Applications

- With `@Configuration` class (for example, `AppConfig.class`):

``` java
AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
```

- Configuration found in any sub-packages of "org.spring.examples.configuration":

``` java
AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("org.spring.examples.configuration");
```
#### Web Application

- A class implementing the `WebApplicationInitializer` can be used to create a Spring application context. The following classes implement the `WebApplicationInitializer` interface:
  - `AbstractContextLoaderInitializer`: Abstract base class that registers a `ContextLoaderListerer` in the servlet context.
  - `AbstractDispatcherServletInitializer`: Abstract base class that registers a `DispatcherServlet` in the servlet context.
  - `AbstractAnnotationConfigDispatcherServletInitializer`: Abstract base class that registers a `DispatcherServlet` in the servlet context and uses Java-based Spring configuration.
  - `AbstractReactiveWebInitializer`: Creates a Spring application context that uses Java-based Spring configuration. Creates a Spring reactive web application in the servlet container.
  
#### `AnnotationConfigWebApplicationContext` with `WebApplicationInitializer`

``` java
public class WebConfig implements WebApplicationInitializer {
    @Override
    public void onStartup(ServletContext servletContext) {
        // Create Root Context and load Root web application configuration
        var rootContext = new AnnotationConfigWebApplicationContext();
        rootContext.register(SecurityConfig.class, PostgresDbConfig.class, ServiceConfig.class);
        servletContext.addListener(new ContextLoaderListener(rootContext));
        
        // Create Servlet Context and load Servlet web application configuration
        var dispatcherContext = new AnnotationConfigWebApplicationContext();
        dispatcherContext.register(WebConfig.class);
        
        // Create and register the DispatcherServlet
        DispatcherServlet servlet = new DispatcherServlet(dispatcherContext);
        ServletRegistration.Dynamic registration = servletContext.addServlet("app", servlet);
        registration.setLoadOnStartup(1);
        registration.addMapping("/app/*");
    }
}
```
Reference: https://docs.spring.io/spring-framework/docs/current/reference/html/web.html#mvc-servlet

#### `AbstractAnnotationConfigDispatcherServletInitializer`

``` java
class WebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{
            SecurityConfig.class,
            PostgresDbConfig.class,
            ServiceConfig.class
        };
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{
            WebConfig.class
        };
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    @Override
    protected Filter[] getServletFilters() {
        CharacterEncodingFilter cef = new CharacterEncodingFilter();
        cef.setEncoding("UTF-8");
        cef.setForceEncoding(true);
        return new Filter[]{new HiddenHttpMethodFilter(), cef};
    }

    @Override
    protected DispatcherServlet createDispatcherServlet(WebApplicationContext servletAppContext) {
        final DispatcherServlet dispatcherServlet = (DispatcherServlet) super.createDispatcherServlet(servletAppContext);
        dispatcherServlet.setThrowExceptionIfNoHandlerFound(true);
        return dispatcherServlet;
    }
}
```
Reference: https://docs.spring.io/spring-framework/docs/current/reference/html/web.html#mvc-servlet-context-hierarchy


----------

### Can you describe the lifecycle of a Spring Bean in an `ApplicationContext`?

The lifecycle of a Spring bean looks like this:

- Spring bean configuration is read and **metadata** in the form of a `BeanDefinition` object is created for each bean.
- All instances of `BeanFactoryPostProcessor` are invoked in sequence and are allowed an opportunity to alter the bean **metadata**.
- For each bean in the container:
  - An instance of the bean is created using the bean metadata.
  - Properties and dependencies of the bean are set.
  - Any instances of `BeanPostProcessor` are given a change to process the new bean instance before and after initialization.
- Any methods in the bean implementation class annotated with `@PostConstruct` are invoked. 
  - This processing is performed by a `BeanPostProcessor`.
- Any `afterPropertiesSet` method in a bean implementation class implementing the `InitializingBean` interface is invoked.
  - This processing is performed by a `BeanPostProcessor`. If the same initialization method has already bean invoked, it will not be invoked again.
- Any custom bean initialization method is invoked.
  - Bean initialization methods can be specified either in the value of the `init-method` attribute in the corresponding `<bean>` in a Spring XML configuration or in the `initMethod` property of the `@Bean` annotation.
  - This processing is performed by a `BeanPostProcessor`. If the same initialization method has already bean invoked, it will not be invoked again.
- The bean is ready for use.
- When the Spring application context is to shut down, the beans in it will receive destruction callbacks in this order:
  - Any methods in the bean implementation class annotated with `@PreDestroy` are invoked.
  - Any `destroy` method in a bean implementation class implementing the `DisposableBean` interface is invoked.
    - If the same destruction method has already been invoked, it will not be invoked again.
  - Any custom bean destruction method is invoked.
    - Bean Destruction methods can be specified either in the value of the `destroy-method` attribute in the corresponding `<bean>` element in s Spring XML configuration or in the `destroyMethod` property of the `@Bean` annotation.
    - If the same destruction method has already been invoked, it will not be invoked again.

<p align="center">
  <img src="img/bean-lifecycle.png" alt="Bean Lifecycle" width="100%"/>
</p>


----------

### How are you going to create an `ApplicationContext` in an integration test?

`@ContextConfiguration` defines class-level metadata that is used to determine how to load and configure an `ApplicationContext` for **Integration Tests**. (Spring framework)

#### `JUnit 4`
- `@RunWith(SpringJUnit4ClassRunner.class)` or `@RunWith(SpringRunner.class)`
- Must have `@ContextConfiguration` to tell the runner class where the bean definitions come from. For example:
  - `@ContextConfiguration(loader = AnnotationConfigContextLoader.class)`
  - `@ContextConfiguration(classes = {TestDbConfig.class, RepoConfig.class})`

``` java
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {TestDbConfig.class, RepoConfig.class})
public class RepositoryTest {}
```
- To use Mockito, `@RunWith(MockitoJUnitRunner.class)`

``` java
@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(classes = {TestDbConfig.class, RepoConfig.class})
public class RepositoryTest {}
```

#### `JUnit 5`

- `@ExtendWith(SpringExtension.class)`

``` java
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestDbConfig.class, RepoConfig.class})
public class RepositoryTest {}
```

- `@SpringJUnitConfig` = `@ExtendWith(SpringExtension.class)` + `@ContextConfiguration`

``` java
@SpringJUnitConfig(classes = {TestDbConfig.class, RepoConfig.class})
class RepositoryTest {}
```

- To use Mockto, `@ExtendWith(MockitoExtension.class)`

``` java
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = {TestDbConfig.class, RepoConfig.class})
public class RepositoryTest {}
```

----------

### What is the preferred way to close an application context? Does Spring Boot do this for you?

The preferred way to close an application context depends on the type of application.

#### Standalone Application

In a standalone non-web Spring application, there are two ways by which the Spring application context can be closed.

- Registering a shutdown-hook by calling the method `registerShutdownHook`, also implemented in the `AbstractApplicationContext` class.
  - This will cause the Spring application context to be closed when the Java virtual machine is shut down normally. This is the recommended way to close the application context in a non-web application.
  
``` java
var context = new AnnotationConfigApplicationContext(AppConfig.class);
context.registerShutdownHook();
```
  
- Calling the `close` method from the `AbstractApplicationContext` class. 
  - This will cause the Spring application to closed immediately.

``` java
var context = new AnnotationConfigApplicationContext(AppConfig.class);
context.close();
```


#### Web Application

A **standard servlet listener** is used to bootstrap and shutdown the Spring application context. The application context is created and injected into the `DispatcherServlet` before any request is made, and when the application is stopped, the Spring context is closed gracefully. The Spring servlet listener class is `org.springframework.web.context.ContextLoaderListener`.

In a Web application, closing of the Spring application context is taken care of by the `ContextLoaderListener`, which implements the `ServletContextListener` interface. The `ContextLoaderListener` will receive a `ServletContextEvent` when the web container stops the web application.


#### Spring Boot Application

Spring Boot will register a shutdown-hook as described above when a Spring application that uses Spring Boot is started.

The mechanism described above with the `ContextLoaderListerner` also applies to Spring Boot web applications.

----------

### Are beans lazily or eagerly instantiated by default? How do you alter this behavior?

`Singleton` Spring beans in an application context are eagerly initialized by default, as the application context is created.

An instance of a `prototype` scoped bean is typically created lazily when requested. An *exception* is when a `prototype` scoped bean is a dependency of a `singleton` scoped bean, in which case the `prototype` scoped bean will be eagerly initialized.

To explicitly set whether beans are to  be lazily or eagerly initialized, the `@Lazy` annotation can be applied either to:
- Methods annotated with the `@Bean` annotation.
  - Bean will be lazy or not as specified by the boolean parameter to the `@Lazy` annotation (default value is **true**).
- Classes annotated with the `@Configuration` annotation.
  - All beans declared with the configuration class will be lazy or not as specified by the boolean parameter to the `@Lazy` annotation (default value is **true**).
- Classes annotated with `@Component` or any related stereotype annotation.
  - The bean created from the component class will be lazy or not as specified by the boolean parameter to the `@Lazy` annotation (default value is **true**).


**Note**: Try to avoid `@Lazy`, it can cause some errors not catched early.

----------

### What is a property source? How would you use `@PropertySource`?

A property source in Spring's `environment` abstraction represents a source of key-value pairs. Examples of property sources are:

- The system properties of the JVM in which the Spring application is executed. They can be obtained by calling `System.getProperties()`.
- The system environment variables. They can be obtained by callling `System.getenv()`.
- Properties in a JNDI environment.
- Servlet configuration init parameters.
- Servlet context init parameters.
- Properties files.
  - Both traditional properties file format and XML format are supported. See the `ResourcePropertySource` class for details.
  
The `@PropertySource` annotation can be used to add a property source to the Spring environment. The annotation is applied to classes annotated with `@Configuration`. Example:

``` java
@Configuration
@PropertySource("classpath:db.properties")
public class DbConfig {
    @Value("${db.driverClassName}")
    private String driverClassName;
    @Value("${db.url}")
    private String url;
    @Value("${db.username}")
    private String username;
    @Value("${db.password}")
    private String password;
    @Value("${db.dialect}")
    private String dialect;
}

```

----------

### What is a `BeanFactoryPostProcessor` and what is it used for? When is it invoked?

`BeanFactoryPostProcessor` is an interface that contains a single method definition that must be implemented: `postProcessBeanFactory(...)`. It is used to modify Spring bean meta-data prior to instantiation of the beans in a container. A `BeanFactoryPostProcessor` may not create instances of beans, only modify bean meta-data. A `BeanFactoryPostProcessor` is only applied to the meta-data of the beans in the same container in which it is defined in (**scoped per-container**).


Examples of `BeanFactoryPostProcessor` are:

- `PropertySourcesPlaceholderConfigurer`
  - Allows for injection of values from the current Spring environment and its set of `PropertySources`. Typically values from the applicaions properties-file are injected using the `@Value` annotation.
  

Registration
- An `ApplicationContext` auto-detects `BeanFactoryPostProcessor` beans in its bean definitions and applies them before any other beans get created. A `BeanFactoryPostProcessor` may also be registered programmatically with a ConfigurableApplicationContext.


Ordering
- `BeanFactoryPostProcessor` beans that are autodetected in an `ApplicationContext` will be ordered according to `PriorityOrdered` and `Ordered` semantics. In contrast, `BeanFactoryPostProcessor` beans that are registered programmatically with a `ConfigurableApplicationContext` will be applied in the order of registration; any ordering semantics expressed through implementing the `PriorityOrdered` or `Ordered` interface will be ignored for programmatically registered post-processors. Furthermore, the `@Order` annotation is not taken into account for `BeanFactoryPostProcessor` beans.


Reference: https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/beans/factory/config/BeanFactoryPostProcessor.html

----------

### What is a `BeanPostProcessor` and how is it different to a `BeanFactoryPostProcessor`? What do they do? When are they called?

- `BeanPostProcessor`: Factory hook that allows for custom modification of new **bean instances** - for example, checking for marker interfaces or wrapping beans with proxies. It is an interface that defines callback methods that allow for modification of bean instances. There are 2 methods that can be implemented:

    - `postProcessBeforeInitialization`: post-processors that populate beans via marker interfaces or the like will implement this. This is applied **before** any bean initialization callbacks (like `InitializingBean's afterPropertiesSet` or a custom `init-method`).

    - `postProcessAfterInitialization`: post-processors that wrap beans with proxies will normally implement this.


- `BeanFactoryPostProcessor`: Factory hook that allows for custom modification of an application context's **bean definitions**, adapting the bean property values of the context's underlying bean factory.

    - The semantics of this interface are similar to those of the `BeanPostProcessor`, with one major difference: `BeanFactoryPostProcessor` operates on the **bean configuration metadata**. That is, the Spring IoC container lets a `BeanFactoryPostProcessor` read the **configuration metadata** and potentially change it before the container instantiates any beans other than BeanFactoryPostProcessor instances.

    - Source: https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-factory-extension-factory-postprocessors

----------

### What does `component-scanning` do?

Component, or classpath, scanning is the process in which the Spring container searches the classpath for classes annotated with stereotype annotations and registers bean definitions in the Spring container for such classes.

To enable component scanning, annotate a configuration class in your Spring application with the `@ComponentScan` annotation. The default component scanning behavior is to detect classes annotated with `@Component` or an annotation that itself is annotated with `@Component` (`@Controller`, `@Service`, `@Repository`). Note that the `@Configuration` annotation is annotated with the `@Component` annotation and thus are Spring Java configuration classes and `@SpringBootApplication` also candidates for auto-detection using component scanning.

`@SpringBootApplication` inheritance chain
 - `@SpringBootApplication` -> `@SpringBootConfiguration` -> `@Configuration` -> `@Component`

Filtering configuration can be added to the `@ComponentScan` annotation as to include or exclude certain classes.

``` java
@Configuration
@ComponentScan(
               basePackages = { "com.myapp" },
               basePackageClasses = { Service.class },
               excludeFitlers = { @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = Bean.class) }
               includeFitlers = { @ComponentScan.Filter(type = FilterType.REGEX, pattern = "com\\.myapp\\.email\\..*") }
          )
public class AppConfig {}

```

The above example configures component scanning:

- Using `basePackages` property to scan components and beans in the base packages "com.myapp" and its subpackages.
- Using `basePackageClasses` property to scan components and beans in the target class's base packages and its subpackages.
  - It is preferred over `basePackages` property due to type-safe and better support from refactoring tooling.
- `excludeFilters` property will exclude components and beans based on a specified fitler.
- `includeFilters` property will include components and beans based on a specified fitler.

`FilterType`:

- `ANNOTATION`: Filter candidates marked with a given annotation.
- `ASSIGNABLE_TYPE`: Filter candidates assignable to a given type.
- `ASPECTJ`: Filter candidates matching a given AspectJ type pattern expression.
- `REGEX`: Filter candidates matching a given regex pattern.
- `CUSTOM`: Filter candidates using a given custom.

----------

### What is the behavior of the annotation `@Autowired` with regards to field injection, constructor injection and method injection?

Precedence from highest to lowest:

- Type (class, abstract, interface...)
- If there are multiple beans of the same type, then:
  - `@Qualifier` at Injection Point (`@Autowire`)
  - `@Primary`
  - Bean name or alias - `@Bean({"myBean", "myAlias"})`, `@Component("myBean")`, `@Named("myBean")`

If both the `@Qualifier` and `@Primary` annotations are present, then the `@Qualifier` annotation will have precedence. Basically, `@Primary` defines a default, while `@Qualifier` is very specific.

`@Qualifier` at Injection Point (`@Autowired`) can match bean names or existing `@Qualifier("...")` definitions.

However, `@Qualifier` at bean definition (`@Bean` or `@Component`) does NOT add a new bean name or an alias. It can only match against an `@Qualifier` at Injection Point (`@Autowired`).

If there is only one instance of the bean type, it does not really matter the bean name.

If there is no unique matching, exception will be thrown.

If a bean name is not specified:
  - The method name with `@Bean` annotation will be used as bean name.
  - Class name with `@Component` annotation (or its derivatives) will be used as bean name.
    - Bean name will be changed to **lower camel case**.

`@Autowired` cannot be used to autowire primitive type, such as `String`. `@Value` is used for these primitive types.

`@Autowired` supports Generic Types.

`@Autowired` supports Arrays, Collections, and Maps.

There should be only one and only one `@Autowired` constructor in a class

`@Autowired` attribute `required` can only be used with setters, NOT constructor. Constructor injection is always **mandatory**.
  - `@Required` is alternative for `required` attribute but deprecated.

`@Autowired` Constructor and Setter can be used together.

Setter method name does NOT NEED to start with `set...()` to be autowired.

Typed Map collection can be autowired as long as the expected key type is String.

``` java
@Autowired
public void setMovieCatalogs(Map<String, MovieCatalog> movieCatalogs) {
    this.movieCatalogs = movieCatalogs;
}
```

----------

### How does the `@Qualifier` annotation complement the use of `@Autowired`?

The `@Qualifier` annotation can be used at these different locations:
- At Injection points
- At bean definitions
- At stereotype annotations (Classes annotated with stereotype annotation is a type of bean definition)
- At annotation definitions (This creates a customer qualifier annotation)

#### `@Qualifier` at Injection Points

The `@Qualifier` annotation can aid in selecting one single bean to be dependency-injected into a field or parameter annotated with `@Autowired` when there are multiple candidates. The most basic use of the `@Qualifier` annotation is to specify the name of the Spring bean to be selected to be dependency-injected.

#### `@Qualifier` at Bean Definitions

Qualifiers can also be applied on bean definitions by annotating a method annotated with `@Bean` in a configuration class with `@Qualifier` and supplying a value in the `@Qualifier` annotation. This will assign a qualifier to the bean and the same qualifier can later be used at an injection point to inject the bean in question.

If a bean has not been assigned a qualifier, the default qualifier, being the name of the bean, will be assigned to the bean.

#### `@Qualifier` at Stereotype Annotation

Similar to qualifiers at bean definition, the `@Qualifier` annotation can also be used at the same place, that is class level, to accompany stereotype annotations like `@Component`, `@Repository`, `@Service` etc. This will have the same effect at annotating a bean definition with the `@Qualifier` annotation and the same qualifier can be used at an injection point to inject the bean created from the annotated component, repository, service etc.

#### `@Qualifier` at Annotation Definitions

Annotation definitions can be annotated with the `@Qualifier` annotation in order to create custom qualifier annotations.

----------

### What is a `proxy` object and what are the two different types of proxies Spring can create?

- `Proxy` object is an object that adds additional logic on top of object that is being proxied without having to modify code of proxied object. Proxy object has the same public methods as object that is being proxied and it should be as much as possible indistinguishable from proxied object. When method is invoked on Proxy Object, additional code, usually before and after sections are invoked, also code from proxied object is invoked by Proxy Object.

- Proxy Advantages:
  - Ability to change behavior of existing beans without changing original code.
  - Separation of concerns (logging, transactions, security, ...).
  
- Proxy Disadvantages:
  - May create code hard to debug.
  - Needs to use unchecked exception for exceptions not declared in original method.
  - May cause performance issues if before/after section in proxy code is using IO (Network, Disk)
  - May cause unexpected equals operator (`==`) results since Proxy Object and Proxied Object are two different objects.

- Spring can create `CGLIB proxy` and `JDK Dynamic proxy`.

- Overview of `CGLIB Proxies`:
  - Generate a new class that subclasses the target class and wrap the target object at runtime.
  
- Overview of `JDK Dynamic Proxies`:
  - Generate a new class that implements the same interface as target class and wrap the target object at runtime.


----------

### What does the `@Bean` annotation do?

The `@Bean` annotation tells the Spring container that the method annotated with the `@Bean` annotation will instantiate, configure and initialize an object that is to be managed by the Spring container. In addition, there are the following optional configuration that can be made in the `@Bean` annotation:

- Configure autowiring of dependencies; whether by name or type.
- Configure a method to be called during bean initialization (`initMethod`)
  - As before, this method will be called after all the properties have been set on the bean but before the bean is taken in use.

``` java
@Bean(initMethod = "beanInitMethod")
FunBean funBean(){}

@Component
public class FunBean {
    void beanInitMethod() {
        // ...
    }
}

```

- Configure a method to be called on the bean before it is discarded (`destroyMethod`)

``` java
@Bean(destroyMethod = "beanDestroyMethod")
FunBean funBean(){}

@Component
public class FunBean {
    void beanDestroyMethod() {
        // ...
    }
}
```

- Specify the name and aliases of the bean.
  - An alias of a bean is an alternative bean-name that can be used to reference the bean.

``` java
// Set bean name to "myBean" and alias to "beanAlias"
@Bean({"myBean", "beanAlias"})
FunBean funBean(){}
```

The default bean name is the name of the method annotated with the `@Bean` annotation and it will be used if there are no other name specified for the bean.

----------

### What is the default bean id if you only use `@Bean`? How can you override this?

As in the previous section, the default bean name, also called bean id, is the name of the `@Bean` annotated method. This default id can be overriden using the `name`, or its alias `value`, attribute of the `@Bean` annotation.

Currently we cannot create **aliases** for stereotype annotation, such as `@Component`.

This can be done with `@Bean` annotation:
  - The first alias will be the unique identifier for the bean.
  - Everything after that will be treated as alias.

``` java
// Set bean name to "myBean" and alias to "beanAlias"
@Bean({"myBean", "beanAlias"})
FunBean funBean(){}

```

----------

### Why are you not allowed to annotate a final class with `@Configuration`?

The Spring container will create a subclass of each class annotated with `@Configuration` when creating an applicaion context using CGLIB. Final classes cannot be subclassed, thus classes annotated with `@Configuration` cannot be declared as final. 

The reason for the Spring container subclassing `@Configuration` classes is to control bean creation - for single beans, subsequent requests to the method creating the bean shoul return the same bean isntance as created at the first invocation of the `@Bean` annotated method.

#### How do `@Configuration` annotated classes support singleton beans?

Singleton beans are supported by the Spring container by sublassing classes annotated with `@Configuration` and overriding the `@Bean` annotated methods in the class. Invocations to the `@Bean` annotated methods are intercepted and, if a bean is a singleton bean and no instance of the singleton bean exists, the call is allowed to continue to the `@Bean` annotated method, in order to create an instance of the bean. If an instance of the singleton bean already exists, the existing instance is returned (and the call is not allowed to continue to the `@Bean` annotated method).

#### Why can't `@Bean` methods be final either?

As earlier the Spring container subclass classes `@Configuration` classes and overrides the methods annotated with the `@Bean` annotation, in order to intercept requests for the beans. If the bean is a singleton bean, subsequent requests for the bean will not yield new instances, but the existing instance of the bean.

----------

### How do you configure `profiles`? What are possible use cases where they might be useful?

A profile is a logical group of bean definitions that is registered within the Spring IoC container when the profile is active. Using profiles within a Spring application is practical because it becomes easier to activate configuration for one environment or another.

`Profile` is a mechanism that allows for registering different beans depending on different conditions. Some examples of such conditions are:
- Testing and development
  - Certain beans are only to be created when running tests. When developing, an in-memory database is to be used, but when deploying to production, a regular database is to be used.
  - Performance monitoring
  - Application customization for different markets, customers, etc.


The `@Profile` annotation may be used in any of the following ways:

- as a type-level annotation on any class directly or indirectly annotated with `@Component`, including `@Configuration` classes
- as a meta-annotation, for the purpose of composing custom stereotype annotations
- as a method-level annotation on any `@Bean` method

`@Profile({"p1", "!p2"})`, registration will occur if profile `p1` is active **OR** if profile `p2` is not active.

`@Profile({"p1", "p2"})`, that class will not be registered or processed unless at least profile `p1` OR `p2` has been activated.

Source: https://docs.spring.io/spring-framework/docs/4.3.12.RELEASE/javadoc-api/org/springframework/context/annotation/Profile.html


One or more profiles can be activated using one of the following options:

- Programmatic registration of active profiles when the Spring application context is created.
- Using the `spring.profiles.active` property
- In test, the `@ActiveProfiles` annotation may be applied at class level to the test class specifying which profile(s) that are to be activated when the tests in the class are run.

There is a default profile named `default` that will be active if no other profile is activated.

If beans are not annotated with `@Profile`, they will be always included in IoC container.

----------

### Can you use `@Bean` together with `@Profile`?

Yes, see above.

----------

### Can you use `@Component` together with `@Profile`?

Yes, see above.

----------

### How many `profiles` can you have?

- Almost unlimited
- `Integer.Max` (due to `for` loop using `int`)
- 2^31

----------

### How do you inject scalar/literal values into Spring beans?

Scalar/literal values can be injected into Spring beans using the `@Value` annotation. Such values can originate from environment variables, property files, Spring beans etc.

``` java
@Component
public class MyBeanClass {
    @Value("${value-to-be-injected:default-literal-value}")

    @Value("${another-value:{backup-value}}")

    @Value("#{ T(java.lang.Math).random() * 50.0 }")
}

The `@Value` annotation can be applied to:

- Fields
- Methods (typically setter methods)
- Method parameters 
  - Including constructor parameters. Note that when annotating a parameter in a method other than a constructor, automatic dependency injection will not occur. If automatic injection of the value is desired, the `@Value` annotation should be moved to the method instead.
- Definition of annotations
  - In order to create a custom annotation.

```

----------

### What is Spring Expression Language (`SpEL` for short)?

----------

### What is the Environment abstraction in Spring?

----------

### Where can properties in the environment come from – there are many sources for properties – check the documentation if not sure. Spring Boot adds even more.

----------

### What can you reference using `SpEL?`

----------

### What is the difference between `$` and `#` in `@Value` expressions?


----------


# Extras


### Bean Scopes

| Scope       | Annotation                                                                                              | Description                                                                                                                                                                      |
|-------------|---------------------------------------------------------------------------------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| singleton   | none <br> `@Scope("singleton")` <br> `@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)`                  | The Spring IoC creates a single instance of this bean, and any request for beans with a name (or aliases) matching this bean definition results in this instance being returned. |
| prototype   | `@Scope("prototype")` <br> `@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)`                            | Every time a request is made for this specific bean, the Spring IoC creates a new instance.                                                                                      |
| thread      | `@Scope("thread")`                                                                                      | Introduced in Spring 3.0, it is available, but not registered by default, so the developer must explicitly register it in the same way as if a custom scope would be defined.    |
| request     | `@Scope("request")` <br> `@RequestScope` <br> `@Scope(WebApplicationContext.SCOPE_REQUEST)`             | The Spring IoC creates a bean instance for each HTTP request. Only valid in the context of a web-aware Spring ApplicationContext.                                                |
| session     | `@Scope("session")` <br> `@SessionScope` <br> `@Scope(WebApplicationContext.SCOPE_SESSION)`             | The Spring IoC creates a bean instance for each HTTP session. Only valid in the context of a web-aware Spring ApplicationContext.                                                |
| application | `@Scope("application")` <br> `@ApplicationScope` <br> `@Scope(WebApplicationContext.SCOPE_APPLICATION)` | The Spring IoC creates a bean for the global application context. Only valid in the context of a web-aware Spring ApplicationContext.                                            |
| websocket   | `@Scope("websocket")`                                                                                   | The Spring IoC creates a bean instance for the scope of a WebSocket. Only valid in the context of a web-aware Spring ApplicationContext.                                         |

- Beans that are `singleton`-scoped and set to be pre-instantiated (the default) are created when the container is created.

- If bean A is `singleton`, and it has a property bean B which is a **non-singleton**, every time bean A is acquired by a client, the same instance of bean B is supplied.
  - Use `@Lookup` annotation to inject `prototype`-scoped bean into a `singleton` bean.

- As a rule, use the `prototype` scope for all **stateful** beans and the `singleton` scope for **stateless** beans.

- In contrast to the other scopes, Spring does NOT manage the complete lifecycle of a PROTOTYPE bean: the container instantiates, configures, and otherwise assembles a prototype object, and hands it to the client, with no further record of that prototype instance. Thus, although initialization lifecycle callback methods are called on all objects regardless of scope, in the case of prototypes, configured destruction lifecycle callbacks are not called. The client code must clean up prototype-scoped objects and release expensive resources that the prototype bean(s) are holding. In some respects, the Spring container’s role in regard to a prototype-scoped bean is a replacement for the Java new operator. All lifecycle management past that point must be handled by the client. https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-factory-scopes-prototype

----------

### Why would you define a static `@Bean` method?

You may declare `@Bean` methods as static, allowing for them to be called without creating their containing configuration class as an instance. This makes particular sense when defining post-processor beans, e.g. of type `BeanFactoryPostProcessor` or `BeanPostProcessor`, since such beans will get initialized early in the container lifecycle and should avoid triggering other parts of the configuration at that point.

Note that calls to static `@Bean` methods will never get intercepted by the container, not even within @Configuration classes (see above). This is due to technical limitations: CGLIB subclassing can only override non-static methods. As a consequence, a direct call to another @Bean method will have standard Java semantics, resulting in an independent instance being returned straight from the factory method itself.

> The `PropertySourcesPlaceholderConfigurer` bean declaration needs to be a static method picked up when the context is created, earlier than the configuration class annotated with `@Configuration`, so the property values are added to the Spring Environment and become available for injection in the said configuration class, before this class is initialized.
>
> Since the `PropertySourcesPlaceholderConfigurer` modifies the declaration of a configuration class, this obviously means that these classes are proxied by Spring IoC container, and this obviously means that these classes cannot be final. The infrastructure bean responsible for bootstrapping the processing of `@Configuration` annotated classes is the bean named `InternalConfigurationAnnotationProcessor` and is of `type org.springframework.context.annotation.ConfigurationClassPostProcessor` which is an implementation of `BeanFactoryPostProcessor.`
>
> --- <cite>Iuliana</cite>

Reference: https://docs.spring.io/spring-framework/docs/5.0.5.RELEASE/spring-framework-reference/core.html#beans-factorybeans-annotations

----------

### Which type of excpetion that Spring uses?

Spring prefer `Unchecked Exceptions` as it gives developers freedom of choice as to decide where to implement error handling and removes coupling related to exceptions. It also removes cluttered code as there is no requirement of try-catch blocks.

----------

### What annotatiosn that can affect the order in which the IoC Container instantiates beans?

- `@Order` - directly specifies the order in which beans are instantiated.
- `@Lazy` - makes the Container only instantiate the annotated bean when it is called.
- `@DependsOn` - make sure that the annotated beans are instantiated after their dependencies.
- `@Import` -  make sure that the annotated beans are instantiated after their dependencies.


----------

### `@Bean` *Lite* Mode

`@Bean` methods may also be declared within classes that are not annotated with `@Configuration`. For example, bean methods may be declared in a `@Component` class or even in a *plain old class*. In such cases, a `@Bean` method will get processed in a so-called *'lite'* mode.

Bean methods in *lite* mode will be treated as plain *factory methods* by the container (similar to *factory-method* declarations in XML), with scoping and lifecycle callbacks properly applied. The containing class remains unmodified in this case, and there are no unusual constraints for the containing class or the factory methods.

In contrast to the semantics for bean methods in `@Configuration` classes, *'inter-bean references'* are not supported in lite mode. Instead, when one `@Bean`-method invokes another `@Bean`-method in *lite* mode, the invocation is a standard Java method invocation; Spring does not intercept the invocation via a CGLIB proxy. This is analogous to inter-`@Transactional` method calls where in proxy mode, Spring does not intercept the invocation — Spring does so only in AspectJ mode.

For example:

``` java
@Component
public class Calculator {
    public int sum(int a, int b) {
        return a+b;
    }

    @Bean
    public MyBean myBean() {
        return new MyBean();
    }
}

```

