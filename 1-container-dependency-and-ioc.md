
## What is Dependency Injection?

Dependency injection (DI) is a process whereby objects define their dependencies (that is, the other objects with which they work) only through constructor arguments, arguments to a factory method, or properties that are set on the object instance after it is constructed or returned from a factory method. The container then injects those dependencies when it creates the bean. This process is fundamentally the inverse (hence the name, Inversion of Control) of the bean itself controlling the instantiation or location of its dependencies on its own by using direct construction of classes or the Service Locator pattern.

Code is cleaner with the DI principle, and decoupling is more effective when objects are provided with their dependencies. The object does not look up its dependencies and does not know the location or class of the dependencies. As a result, your classes become easier to test, particularly when the dependencies are on interfaces or abstract base classes, which allow for stub or mock implementations to be used in unit tests.

https://docs.spring.io/spring-framework/docs/current/reference/html/core.html##beans-factory-collaborators

Dependency injection means giving an object its instance variables.

Dependency injection is basically providing the objects that an object needs (its dependencies) instead of having it construct them itself.
It's a very useful technique for testing, since it allows dependencies to be mocked or stubbed out.

Inversion of control is a common characteristic of frameworks
that facilitate injection of dependencies. And the basic idea of the dependency injection
pattern is to have a separate object that injects dependencies with the required behavior,
based on an interface contract.


#### Advantages of Dependency Injection

- Increases cohesion

- Decreases coupling between classes and their dependencies

- Reduces boilerplate code

- Makes program more reusable, maintainable and testable (mocked objects)

- Increases flexibility (by interfaces)


#### Disadvantage of Dependency Injection

- More classes and interfaces



## What is an interface and what are the advantages of making use of them in Java?

Interface is a contract for classes. It makes swapping implementation easy and loose coupling.


## What is an `ApplicationContext`?


## How are you going to create a new instance of an `ApplicationContext`?


## Can you describe the lifecycle of a Spring Bean in an `ApplicationContext`?

## How are you going to create an `ApplicationContext` in an integration test?

## What is the preferred way to close an application context? Does Spring Boot do this for you?

## Are beans lazily or eagerly instantiated by default? How do you alter this behavior?

## What is a property source? How would you use `@PropertySource`?

## What is a `BeanFactoryPostProcessor` and what is it used for? When is it invoked?

## What is a `BeanPostProcessor` and how is it different to a `BeanFactoryPostProcessor`? What do they do? When are they called?

## What does `component-scanning` do?

## What is the behavior of the annotation `@Autowired` with regards to field injection, constructor injection and method injection?

## How does the `@Qualifier` annotation complement the use of `@Autowired`?

## What is a `proxy` object and what are the two different types of proxies Spring can create?

## What does the `@Bean` annotation do?

## What is the default bean id if you only use `@Bean`? How can you override this?

## Why are you not allowed to annotate a final class with `@Configuration`?

## How do you configure `profiles`? What are possible use cases where they might be useful?

The @Profile annotation may be used in any of the following ways:

- as a type-level annotation on any class directly or indirectly annotated with @Component, including @Configuration classes
- as a meta-annotation, for the purpose of composing custom stereotype annotations
- as a method-level annotation on any @Bean method

`@Profile({"p1", "!p2"})`, registration will occur if profile `p1` is active **OR** if profile `p2` is not active.

`@Profile({"p1", "p2"})`, that class will not be registered or processed unless at least profile `p1` OR `p2` has been activated.

Source: https://docs.spring.io/spring-framework/docs/4.3.12.RELEASE/javadoc-api/org/springframework/context/annotation/Profile.html

``` java
/**
   Ensures that any request to our application requires the user to be authenticated

   Allows users to authenticate with form based login

   Allows users to authenticate with HTTP Basic authentication
   ,*/
protected void configure(HttpSecurity http) throws Exception {
    http
        .authorizeRequests()
        .anyRequest().authenticated()
        .and()
        .formLogin()
        .and()
        .httpBasic();
}

System.out.println("HeLlo");
```

## Can you use `@Bean` together with `@Profile`?

## Can you use `@Component` together with `@Profile`?

## How many `profiles` can you have?

- Almost unlimited
- `Integer.Max` (due to `for` loop using `int`)
- 2^31

## How do you inject scalar/literal values into Spring beans?

## What is Spring Expression Language (`SpEL` for short)?

## What is the Environment abstraction in Spring?

## Where can properties in the environment come from – there are many sources for properties – check the documentation if not sure. Spring Boot adds even more.

## What can you reference using `SpEL?`

## What is the difference between `$` and `#` in `@Value` expressions?


# Extras

## Bean Scopes

| Scope       | Annotation                                                                                              | Description                                                                                                                                                                      |
|-------------|---------------------------------------------------------------------------------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| singleton   | none <br> `@Scope("singleton")` <br> `@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)`                  | The Spring IoC creates a single instance of this bean, and any request for beans with a name (or aliases) matching this bean definition results in this instance being returned. |
| prototype   | `@Scope("prototype")` <br> `@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)`                            | Every time a request is made for this specific bean, the Spring IoC creates a new instance.                                                                                      |
| thread      | `@Scope("thread")`                                                                                      | Introduced in Spring 3.0, it is available, but not registered by default, so the developer must explicitly register it in the same way as if a custom scope would be defined.    |
| request     | `@Scope("request")` <br> `@RequestScope` <br> `@Scope(WebApplicationContext.SCOPE_REQUEST)`             | The Spring IoC creates a bean instance for each HTTP request. Only valid in the context of a web-aware Spring ApplicationContext.                                                |
| session     | `@Scope("session")` <br> `@SessionScope` <br> `@Scope(WebApplicationContext.SCOPE_SESSION)`             | The Spring IoC creates a bean instance for each HTTP session. Only valid in the context of a web-aware Spring ApplicationContext.                                                |
| application | `@Scope("application")` <br> `@ApplicationScope` <br> `@Scope(WebApplicationContext.SCOPE_APPLICATION)` | The Spring IoC creates a bean for the global application context. Only valid in the context of a web-aware Spring ApplicationContext.                                            |
| websocket   | `@Scope("websocket")`                                                                                   | The Spring IoC creates a bean instance for the scope of a WebSocket. Only valid in the context of a web-aware Spring ApplicationContext.                                         |

- If bean A is `singleton`, and it has a property bean B which is a **non-singleton**, every time bean A is acquired by a client, the same instance of bean B is supplied.
- Use `@Lookup` annotation to inject `prototype`-scoped bean into a `singleton` bean.

- As a rule, use the `prototype` scope for all **stateful** beans and the `singleton` scope for **stateless** beans.

- In contrast to the other scopes, Spring does not manage the complete lifecycle of a PROTOTYPE bean: the container instantiates, configures, and otherwise assembles a prototype object, and hands it to the client, with no further record of that prototype instance. Thus, although initialization lifecycle callback methods are called on all objects regardless of scope, in the case of prototypes, configured destruction lifecycle callbacks are not called. The client code must clean up prototype-scoped objects and release expensive resources that the prototype bean(s) are holding. In some respects, the Spring container’s role in regard to a prototype-scoped bean is a replacement for the Java new operator. All lifecycle management past that point must be handled by the client. https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-factory-scopes-prototype
