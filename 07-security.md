
### What are authentication and authorization? Which must come first?

----------

### Is security a cross cutting concern? How is it implemented internally?

----------

### What is the delegating filter proxy?

----------

### What is the security filter chain?

----------

### What is a security context?

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
