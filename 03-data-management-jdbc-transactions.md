
### What is the difference between checked and unchecked exceptions?

Checked exceptions are exceptions that the Java compiler requires to be declared in the signature of methods that throw this type of exceptions. If a method calls another method that declares one or more checked exceptions in its method signature, the calling method must either catch these exceptions or declare the exceptions in its method signature.

The class `java.lang.Exception` and its subclasses, except for `java.lang.RuntimeException` and any subclass of `RuntimeException`, are checked exceptions.

Unchecked exceptions are exceptions that the Java compiler does not require to be declared in the signature of methods or to be caught in methods invoking other methods that may throw unchecked exceptions.

#### Why does Spring prefer unchecked exceptions?

Checked exceptions forces developers to either implement error handling in the form of try-catch blocks or to declare exceptions thrown by underlying methods in the method signature.
This can result in cluttered code and/or unnecessary coupling to the underlying methods.

Unchecked exceptions gives developers the freedom of choice as to decide where to implement error handling and removes any coupling related to exceptions.

#### What is the data access exception hierarchy?

The data access exception hierarchy is the `DataAccessException` class and all of its subclasses in the Spring Framework. All the exceptions in this exception hierarchy are unchecked.

The purpose of the data access exception hierarchy is isolate application developers from the particulars of JDBC data access APIs, for instance database drivers from different vendors. This in turn enables easier switching between different JDBC data access APIs.

----------

### How do you configure a DataSource in Spring?

The `javax.sql.DataSource` interface is the interface from which all data-source classes related to SQL stem. The core Spring Framework contain the following root data-source classes and interfaces that all implement this interface:
- DelegatingDataSource
- AbstractDataSource
- SmartDataSource
- EmbeddedDatabase

#### How do you configure a DataSource in Spring?

Obtaining a `DataSource` in a Spring application depends on whether the application is deployed to an application or web server, for example GlassFish or Apache Tomcat, or if the application is a standalone application.

#### `DataSource` in standalone application

After having chosen the appropriate `DataSource` implementation class, a data-source bean is created like any other bean. The following example creates a data-source that retrieves data from a HSQL database using the Apache Commons *DBCP BasicDataSource* data-source:    

``` java
@Bean
public DataSource dataSource() {
  final BasicDataSource theDataSource = new BasicDataSource();
  theDataSource.setDriverClassName("org.hsqldb.jdbcDriver");
  theDataSource.setUrl("jdbc:hsqldb:hsql://localhost:1234/mydatabase");
  theDataSource.setUsername("ivan");
  theDataSource.setPassword("secret");
  return theDataSource;
}
```

If the application uses Spring Boot, then it is not necessary to create a DataSource bean. Setting the values of a few properties are sufficient:

``` java
spring.datasource.url=jdbc:hsqldb:hsql://localhost:1234/mydatabase
spring.datasource.username=ivan
spring.datasource.password=secret
```

#### DataSource in an application deployed to a server

If the application is deployed to an application server then a way to obtain a data-source is by performing a JNDI lookup like in this example:

``` java
@Bean
public DataSource dataSource() {
  final JndiDataSourceLookup theDataSourceLookup = new JndiDataSourceLookup();
  final DataSource theDataSource = theDataSourceLookup.getDataSource("java:comp/env/jdbc/MyDatabase");
  return theDataSource;
}
```

Spring Boot applications need only to rely on setting one single property:

``` java
spring.datasource.jndi-name=java:comp/env/jdbc/MyDatabase
```

----------

### What is the Template design pattern and what is the JDBC template?

----------

### What is a callback? What are the JdbcTemplate callback interfaces that can be used with queries? What is each used for? (You would not have to remember the interface names in the exam, but you should know what they do if you see them in a code sample).

----------

### Can you execute a plain SQL statement with the JDBC template?

----------

### When does the JDBC template acquire (and release) a connection, for every method called or once per
template? Why?

----------

### How does the JdbcTemplate support queries? How does it return objects and lists/maps of objects?

----------

### What is a transaction? What is the difference between a local and a global transaction?

----------

### Is a transaction a cross cutting concern? How is it implemented by Spring?

----------

### How are you going to define a transaction in Spring?

----------

### Is the JDBC template able to participate in an existing transaction?

----------

### What is @EnableTransactionManagement for?

----------

### How does transaction propagation work?

----------

### What happens if one `@Transactional` annotated method is calling another `@Transactional` annotated method inside a same object instance?

----------

### Where can the `@Transactional` annotation be used? What is a typical usage if you put it at class level?

----------

### What does declarative transaction management mean?

----------

### What is the default rollback policy? How can you override it?

----------

### What is the default rollback policy in a JUnit test, when you use the `@RunWith(SpringJUnit4ClassRunner.class)` in JUnit 4 or `@ExtendWith(SpringExtension. class)` in JUnit 5, and annotate your `@Test` annotated method with `@Transactional`?

----------

### Are you able to participate in a given transaction in Spring while working with JPA?

----------

### Which `PlatformTransactionManager(s)` can you use with JPA?

----------

### What do you have to configure to use JPA with Spring? How does Spring Boot make this easier
