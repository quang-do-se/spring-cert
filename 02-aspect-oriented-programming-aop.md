
## What is the concept of AOP? Which problem does it solve? What is a cross cutting concern?

- `AOP` is the acronym for `Aspect-Oriented Programming`, which refers to a type of programming that aims to increase modularity by allowing the separation of cross-cutting concerns.

- `AOP` is a type of programming that aims to help with separation of cross-cutting concerns to increase modularity; it implies declaring an aspect class that will alter the behavior of base code by applying advices to specific join points specified by pointcuts.

- The business or base code is not actually changed, you can imagine aspects as plugins. They modify the behavior, not the actual implementation.

- AOP is a complement of OOP (object-oriented programming). AOP and OOP can be used together to write powerful applications, because both provide different ways of structuring your code. OOP is focused on making everything an object, while AOP introduces the aspect, which is a special type of object that injects and wraps its behavior to complement the behavior of other objects.

- A `cross-cutting concern` is a functionality that is tangled with business code, which usually cannot be separated from the business logic.

- Cross-cutting concerns for an Enterprise Application:
  - Security
  - Caching
  - Logging
  - Monitoring
  - Data validation
  - Internationalization
  - Error detection and correction. Exception handling.
  - Memory management
  - Synchronization
  - Connecting to the database (connection pooling, reusing connections, open and close connections)
  - Transaction

<img src="img/cross-cutting-concern.png" alt="Cross Cutting Concern" height="50%"/>

## What is a pointcut, a join point, an advice, an aspect, weaving?

- `Aspect` : A class containing code specific to a cross-cutting concern. A class declaration is recognized in Spring as an aspect if it is annotated with the @Aspect annotation.
  - A module that encapsulates pointcuts and advice.

- `Weaving` : A synonym for this word is interlacing, but in software the synonym is linking and it refers to aspects being combined with other types of objects to create an advised object. This can be done at compile time (using the AspectJ compiler, for example), load time, or at runtime. Spring AOP, like other pure Java AOP frameworks, performs weaving at `runtime`.

- `Join Point` : A point during the execution of a program, such as the execution of a method or the handling of an exception. I. In Spring AOP, a join point is *always* a method execution. Basically, the join point marks the execution point where aspect behavior and target behavior join.

- `Target object` : An object to which the aspect applies.

- `Target method` : the advised method.

- `Advice` : The action taken by an aspect at a join point. In Spring AOP, there are multiple advice types.
  - @Before advice: Methods annotated with @Before that will execute before the join point. These methods do not prevent the execution of the target method unless they throw an exception.
  - @AfterReturning advice: Methods annotated with @AfterReturning that will execute after a join point completes normally, meaning that the target method returns normally without throwing an exception.
  - @AfterThrowing advice: Methods annotated with @AfterThrowing that will execute after a join point execution ends by throwing an exception.
  - @After (finally) advice: Methods annotated with @After that will execute after a join point execution, no matter how the execution ended.
  - @Around advice: Methods annotated with @Around intercept the target method and surround the join point. This is the most powerful type of advice since can perform custom behavior before and after the invocation. It has the responsibility of choosing to perform the invocation or return its own value, and it provides the option of stopping the propagation of an exception.

- `Pointcut` : A predicate used to identify join points. Advice definitions are associated with a pointcut expression and the advice will execute on any join point matching the pointcut expression. Pointcut expressions are defined using AspectJ Pointcut Expression Language3 Pointcut expressions can be defined as arguments for Advice annotations or as arguments for the @Pointcut annotation.

- `Introduction`: Declaring additional methods, fields, interfaces being implemented, and annotations on behalf of another type. Spring AOP allows this using a suite of AspectJ @Declare* annotations that are part of the aspectjrt library.

- `AOP proxy`: The object created by AOP to implement the aspect contracts. In Spring proxy objects can be JDK dynamic proxies or CGLIB proxies. By default, the proxy objects are JDK dynamic proxies, and the object being proxied must implement an interface that is also implemented by the proxy object. But a library like CGLIB can create proxies by subclassing, so an interface is not needed.


## How does Spring solve (implement) a cross cutting concern?


## Which are the limitations of the two proxy-types?


## How many advice types does Spring support? Can you name each one?


## If shown pointcut expressions, would you understand them?


## What is the `JoinPoint` argument used for?

`JoinPoint` provides reflective access to both the state available at a `join point` and `static` information about it.

It must be the **first** parameter of the advice method.

The `JoinPoint` interface provides a number of useful methods:

- `getArgs()`: Returns the method arguments.

- `getThis()`: Returns the proxy object.

- `getTarget()`: Returns the target object.

- `getSignature()`: Returns a description of the method that is being advised.

- `toString()`: Prints a useful description of the method being advised.

`JoinPoint.StaticPart` contains only the static information about a join point.


## What is a `ProceedingJoinPoint`? Which advice type is it used with?

It's a special `JoinPoint` used by `@Around` advice.

`@Around` advice runs "around" a matched method’s execution. It has the opportunity to do work both before and after the method runs and to determine when, how, and even if the method actually gets to run at all.

The first parameter of the `@Around` advice method must be of type `ProceedingJoinPoint`. Within the body of the advice, calling `proceed()` on the `ProceedingJoinPoint` causes the underlying method to execute. The proceed method may also be called passing in an `Object[]` - the values in the array will be used as the arguments to the method execution when it proceeds.

Note that proceed may be invoked once, many times, or not at all within the body of the around advice, all of these are quite legal.

Example (caching method return):
``` java
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.ProceedingJoinPoint;

@Aspect
public class AroundExample {
    // Inject cache object

    @Around("execution(* com.xyz.myapp.service.*.cached*(..))")
    public Object cacheMethodReturn(ProceedingJoinPoint joinPoint) throws Throwable {
        // Get cache based on target method's arguments
        Object cached = cache.get(joinPoint.getArgs());

        if (cached != null) {
            return cached;
        }

        Object result = joinPoint.proceed();
        cache.put(joinPoint.getArgs(), result);

        return retVal;
    }

}
```
