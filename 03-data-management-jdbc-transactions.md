
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

#### What is the Template design pattern?

Template design pattern is a behavioral design pattern that allows you to defines a skeleton of an algorithm in a base class and let subclasses override the steps without changing the overall algorithm’s structure.

#### What is the JDBC template?

The Spring `JdbcTemplate` class is a Spring class that simplifies the use of JDBC by implementing common workflows for querying, updating, statement execution etc. Some benefits of using the `JdbcTemplate` class are:

- Simplification
  - Reduces the amount of (boilerplate) code necessary to perform JDBC operations.

- Handles exceptions
  - Exceptions are properly handled ensuring that resources are closed or released.

- Translates exceptions
  - Exceptions are translated to the appropriate exception in the `DataAccessException` hierarchy (unchecked exceptions) which are also vendor-agnostic.

- Avoids common mistakes
  - For example, ensures that statements are property closed and connections are released after having performed JDBC operations.

- Allows for customization of core functionality.
  - An example is exception translation. See the above section on the template design pattern.

- Allows for customization of per-use functionality.
  - An example is mapping of rows in a result set to a Java object. This is accomplished using callbacks. Please refer to the next section for further details.
  
Instances of `JdbcTemplate` are thread-safe after they have been created and configured.

----------

### What is a callback? What are the JdbcTemplate callback interfaces that can be used with queries? What is each used for? (You would not have to remember the interface names in the exam, but you should know what they do if you see them in a code sample).

A callback is code or reference to a piece of code that is passed as an argument to a method that, at some point during the execution of the methods, will call the code passed as an argument.In Java a callback can be a reference to a Java object that implements a certain interface or, starting with Java 8, a lambda expression.

- `RowMapper<T>`
  - When each row of the ResultSet maps to a domain object.
  - Stateless and reusable.
  - Per-row basis.
  - **Note** that the `mapRow(...)` method in this interface returns *a Java object*.

- `RowCallbackHandler`
  - When no value should be returned.
  - Typically stateful.
  - Per-row basis.
  - **Note** that the `processRow(...)` method in this interface has *a void return type*.

- `ResultSetExtractor<T>`
  - When multiple rows, or multiple records from different tables returned in a ResultSet map to a single object.
  - Typically stateless and reusable.
  - Can access the whole ResultSet.
  - **Note** that the `extractData(...)` method in this interface returns *a Java object*.
  
----------

### Can you execute a plain SQL statement with the JDBC template?

Plain SQL statements can be executed using the `JdbcTemplate` class. 

The following methods accept one or more SQL strings as parameters. Note that there may be multiple versions of a method that take different parameters:

- `query(...)` for SELECT
- `update(...)` for INSERT, UPDATE, DELETE
- `execute(...)` for Data Definition Language. It can execute any arbitrary SQL.

- `batchUpdate`
- `queryForList`
- `queryForMap`
- `queryForObject`
- `queryForRowSet`

----------

### When does the JDBC template acquire (and release) a connection, for every method called or once per template? Why?

`JdbcTemplate` acquire and release a database connection for every method called. That is, a connection is acquired immediately before executing the operation at hand and released immediately after the operation has completed, be it successfully or with an exception thrown.

The reason for this is to avoid holding on to resources (database connections) longer than necessary and creating as few database connections as possible, since creating connections can be a potentially expensive operation. When database connection pooling is used connections are returned to the pool for others to use.

----------

### How does the JdbcTemplate support queries? How does it return objects and lists/maps of objects?

Jdbc Template supports generic queries with following methods:

- `queryForObject` - returns single object, expects query to return only one record, if this requirement is not matched `IncorrectResultSizeDataAccessException` will be thrown.

- `queryForList` - returns a list of objects of the declared type, expects the query to return results with only one column, otherwise, `IncorrectResultSetColumnCountException` will be thrown.
  - It can also return `List<Map<String,Object>>` if there is no declared return type. It return a List that contains a Map per row.

- `queryForMap` - returns map for a single row with keys representing column names and values representing database record value, expects query to return only one record, if this requirement is not matched `IncorrectResultSizeDataAccessException` will be thrown.

Reference: https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/jdbc/core/JdbcTemplate.html

----------

### What is a transaction? What is the difference between a local and a global transaction?

#### What is a transaction?

A transaction is an operation that consists of a number of tasks that takes place as a single unit - either all tasks are performed or no tasks are performed. If a task that is part of a transaction do not complete successfully, the other tasks in the transaction will either not be performed or, for tasks that have already been performed, be reverted.

A reliable transaction system enforces the ACID principle:

- Atomicity
  - The changes within a transaction are either all applied or none applied. **All or nothing**.
  
- Consistency
  - Any integrity constraints, for instance of a database, are not violated.
  
- Isolation
  - Transactions are isolated from each other and do not affect each other.
  
- Durability
  - Changes applied as the result of a successfully completed transaction are durable.

#### What is the difference between a local and a global transaction?

**Global transactions** allow for transactions to span multiple transactional resources. As an example consider a global transaction that spans a database update operation and the posting of a message to the queue of a message broker. If the database operation succeeds but the posting to the queue fails then the database operation will be rolled back (undone). Similarly if posting to the queue succeeds but the database operation fails, the message posted to the queue will be rolled back and will thus not appear on the queue. Not until both operations succeed will the database update come into effect and a message be made available for consumption on the queue. 

Note that a transaction that is to span operations on two different databases needs to be a global transaction.

**Local transactions** are transactions associated with one single resource, such as one single database or a queue of a message broker, but not both in one and the same transaction.

----------

### Is a transaction a cross cutting concern? How is it implemented by Spring?

Transaction management is a cross-cutting concern. 

In the Spring framework declarative transaction management is implemented using Spring AOP.

----------

### How are you going to define a transaction in Spring?

The following two steps are all required to use Spring transaction management in a Spring application:

- Declare a `PlatformTransactionManager` bean.
  - Choose a class implementing this interface that supplies transaction management for the transactional resource(s) that are to be used. Some examples are `JmsTransactionManager` (for a single JMS connection factory), `JpaTransactionManager` (for a single JPA entity manager factory), `JtaTransactionManager` ( when working with multiple databases and entity managers. Furthermore, it can be suitable when working with more than one transactional resource).
  
- If using annotation-driven transaction management, then apply the `@EnableTransactionManagement` annotation to exactly one `@Configuration` class in the application.

- Declare transaction boundaries in the application code. This can be accomplished using one or more of the following:
  - `@Transactional` annotation
    - It is a declarative transaction management and can be applied on methods and classes.
    - Spring allows for using the JPA `javax.transaction.Transactional` annotation as a replacement for the Spring `@Transactional` annotation.
  - Spring XML configuration (not in the scope of this book).
  - Programmatic transaction management with `TransactionTemplate` class. Explicitly setting the transaction name is something that can only be done programmatically. For example:

``` java
@Autowired
EntityManager entityManager;

public void savePayment(Long amount) {

  TransactionTemplate txTemplate = new TransactionTemplate(transactionManager);

  return txTemplate.execute(transactionStatus -> {
      Payment payment = new Payment(amount);

      try {
        entityManager.persist(payment);
      } catch(Exception e) {
        transactionStatus.setRollbackOnly();
      }
    });
}

```

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
