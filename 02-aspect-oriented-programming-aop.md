## What is the concept of AOP? Which problem does it solve? What is a cross cutting concern?


## What is a pointcut, a join point, an advice, an aspect, weaving?


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
