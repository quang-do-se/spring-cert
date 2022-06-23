
### What are authentication and authorization? Which must come first?

Authentication and authorization are two fundamental concepts in the realm of application security.

#### Authentication

The short explanation of authentication is that it is the process of verifying that, for instance, a user of a computer system is who he/she claims to be.

Authentication is the process of confirming that some piece of information is true. An example is the login of a computer system; a user enters a user name and a password. The password is a secret that is used to confirm that the person entering the username is indeed the user he/she claims to be.

The user proves his/her identity to the computer system.

Apart from the traditional username and password way of authentication, there are other ways of authenticating a user of a computer system such as PIN numbers, security questions, id cards, fingerprints etc etc.

In Spring Security, the authentication process consists of the following steps quoted from the Spring Security reference:

- The username and password are obtained and combined into an instance of `UsernamePasswordAuthenticationToken` (an instance of the `Authentication` interface).
- The token is passed to an instance of `AuthenticationManager` for validation.
- The `AuthenticationManager` returns a fully populated `Authentication` instance on successful authentication.
- The security context is established by calling `SecurityContextHolder.getContext().setAuthentication(…)`, passing in the returned authentication object.

#### Authorization
The short explanation of authorization is the process of determining that a user of a computer system is permitted to do something that the user is attempting to do.Authorization is the process of specifying access rights to resources. For instance, the only type of users that can create and delete users in a computer system is users is users in the administrator role. Thus the only users that have access to the create and delete functions of the application are users in the administrator role. 

#### Which must come first?

Unless there is some type of authorization that specifies what resources and/or functions that can be accessed by anonymous users, authentication must always come before authorization.This in order to be able to establish the identity of the user before being able to verify what the user is allowed to do.

----------

### Is security a cross cutting concern? How is it implemented internally?

Yes.

#### How is Security implemented internally in Spring Security?

Spring Security is implemented in the following two ways depending on what is to be secured:

- Using a Spring AOP proxy that inherits from the `AbstractSecurityInterceptor` class. Applied to **method** invocation authorization on objects secured with Spring Security.

- Spring Security’s web infrastructure is based entirely on Servlet Filters.

#### Spring Security Web Infrastructure

- **All** the requests are handled by `DelegatingFilterProxy` and it sends the request to `FilterChainProxy` for handling further Authentication flow.

- `DelegatingFitlerProxy` is a bridge between Servlet container's life cycle and Spring's ApplicationContext
  - D`elegatingFilterProxy` is a Servlet Filter. `Servlet Filters` are executed just before the servlets are executed. So any security mechanism like authentication are implemented using filters, so that a valid user is accessing the secured resource.

- `DelegatingFilterProxy` can be registered via standard Servlet container mechanisms, but delegate all the work to a Spring Bean that implements Filter (`FilterChainProxy`).

- `FilterChainProxy` is a special Filter provided by Spring Security that allows delegating to many Filter instances through `SecurityFilterChain`.

- `SecurityFilterChain` associates a request URL pattern with a list of filters.
  - Filters under `SecurityFilterChain` are `GenericFilterBeans`, which are Spring `Filters`. These are also `Servlet Filters`, but have Spring implementation.

<p align="center">
  <img src="img/security-infrastructure.png" alt="Security Infrastructure" width="70%"/>
</p>

<p align="center">
  <img src="img/security.png" alt="Security" width="70%"/>
</p>

----------

### What is the delegating filter proxy?

----------

### What is the security filter chain?

----------

### What is a security context?

#### Spring Security Core Components

| Component Type          | Function                                                                                                                                                                                                                                                                 |
|-------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `SecurityContextHolder` | Contains and provides access to the SecurityContext of the application. Default behavior is to associate the SecurityContext with the current thread.                                                                                                                    |
| `SecurityContext`       | Default and only implementation in Spring Security holds an Authentication object. May also hold additional request-specific information.                                                                                                                                |
| `Authentication`        | Represents token for authentication request or authenticated principal after the request has been granted. Also contains the authorities in the application that an authenticated principal has been granted.                                                            |
| `GrantedAuthority`      | Represents an authority granted to an authenticated principal.                                                                                                                                                                                                           |
| `UserDetails`           | Holds user information, such as user-name, password and authorities of the user. This information is used to create an `Authentication` object on successful authentication. May be extended to contain application-specific user information.                           |
| `UserDetailsService`    | Given a user-name this service retrieves information about the user in a `UserDetails` object. Depending on the implementation of the user details service used, the information may be stored in a database, in memory or elsewhere if a custom implementation is used. |

<p align="center">
  <img src="img/security-context.png" alt="Security Context" width="80%"/>
</p>


----------

### What does the `**` pattern in an antMatcher or mvcMatcher do?

----------

### Why is the usage of mvcMatcher recommended over antMatcher?

----------

### Does Spring Security support password encoding?

----------

### Why do you need method security? What type of object is typically secured at the method level (think of its purpose not its Java type).

`@Secured` annotation is both method-level and class-level annotation.

- To enable Method Security, add `@EnableGlobalMethodSecurity(secureEnabled = true)` on a Configuration class and add `@Secured("<ROLE>")` on the target method.
  - It causes the class containing the method to be wrapped in a secure proxy (AOP) to restrict access only to users with certain `<ROLE>`
  - `@Secured` is usually used in Service class.

----------

### What do @PreAuthorized and @RolesAllowed do? What is the difference between them?

----------

### How are these annotations implemented?

----------

### In which security annotation, are you allowed to use SpEL?
