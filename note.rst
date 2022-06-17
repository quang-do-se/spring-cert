What is Dependency Injection?
-----------------------------

Dependency injection (DI) is a process whereby objects define their dependencies (that is, the other objects with which they work) only through constructor arguments, arguments to a factory method, or properties that are set on the object instance after it is constructed or returned from a factory method. The container then injects those dependencies when it creates the bean. This process is fundamentally the inverse (hence the name, Inversion of Control) of the bean itself controlling the instantiation or location of its dependencies on its own by using direct construction of classes or the Service Locator pattern.

Code is cleaner with the DI principle, and decoupling is more effective when objects are provided with their dependencies. The object does not look up its dependencies and does not know the location or class of the dependencies. As a result, your classes become easier to test, particularly when the dependencies are on interfaces or abstract base classes, which allow for stub or mock implementations to be used in unit tests.

https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-factory-collaborators

---

Dependency injection means giving an object its instance variables.

Dependency injection is basically providing the objects that an object needs (its dependencies) instead of having it construct them itself.
It's a very useful technique for testing, since it allows dependencies to be mocked or stubbed out.

---

Inversion of control is a common characteristic of frameworks
that facilitate injection of dependencies. And the basic idea of the dependency injection
pattern is to have a separate object that injects dependencies with the required behavior,
based on an interface contract.


Advantages of Dependency Injection
---------------------------------

* Increases cohesion

* Decreases coupling between classes and their dependencies

* Reduces boilerplate code

* Makes program more reusable, maintainable and testable (mocked objects)

* Increases flexibility (by interfaces)

  
Disadvantage of Dependency Injection
------------------------------------

* More classes and interfaces
  
  
