
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

#### What is the `PlatformTransactionManager`?

`PlatformTransactionManager` is the base interface for all transaction managers that can be used in the Spring framework’s transaction infrastructure. Transaction managers (implementing this interface) can be used directly by applications, but it is recommended to use declarative transactions or the `TransactionTemplate` class.

The `PlatformTransactionManager` interface contain the following methods:

- `void commit(TransactionStatus)`: Commits or rolls back the transaction related to the `TransactionStatus` object transaction depending on its status.

- `void rollback(TransactionStatus)`: Rolls back the transaction related to the `TransactionStatus` object.

- `TransactionStatus getTransaction(TransactionDefinition)`: Creates a new transaction and return it or return the currently active transaction, depending on how transaction propagation has been configured.

----------

### Is the JDBC template able to participate in an existing transaction?

Yes, the `JdbcTemplate` is able to participate in existing transactions both when declarative and programmatic transaction management is used. This is accomplished by wrapping the `DataSource` using a `TransactionAwareDataSourceProxy`.

----------

### What is `@EnableTransactionManagement` for?

The `@EnableTransactionManagement` annotation is to annotate exactly one configuration class in an application in order to enable annotation-driven transaction management using the `@Transactional` annotation.

Components registered when the `@EnableTransactionManagement` annotation is used are:
- A `TransactionInterceptor`.
  - Intercepts calls to `@Transactional` methods creating new transactions as necessary etc.
- A `JDK Proxy` or `AspectJ` advice.
  - This advice intercepts methods annotated with `@Transactional` (or methods that are located in a class annotated with `@Transactional`).
  
The `@EnableTransactionmanagement` annotation have the following three optional elements:

- mode
  - Allows for selecting the type of advice that should be used with transactions. Possible values are `AdviceMode.ASPECTJ` and `AdviceMode.PROXY` (DEFAULT) with the latter being the default.
  
- order
  - Precedence of the transaction advice when more than one advice is applied to a join-point. Default value is `Ordered.LOWEST_PRECEDENCE`.
  
- proxyTargetClass
  - True if CGLIB proxies are to be used, false if JDK interface-based proxies are to be used in the application (affects proxies for all Spring managed beans in the application!). Applicable only if the mode element is `AdviceMode.PROXY`.

----------

### How does transaction propagation work?

#### Transaction Propagation

Transaction propagation determines the way an existing transaction is used, depending on the transaction propagation configured in the `@Transactional` annotation on the method, when the method is invoked.

There are seven different options available when setting the propagation in a `@Transactional` annotation, all defined in the Propagation enumeration:

- MANDATORY
  - There must be an existing transaction when the method is invoked, or an exception will be thrown.

- NESTED
  - Executes in a nested transaction if a transaction exists, otherwise a new transaction will be created. This transaction propagation mode is not implemented in all transaction managers.

- NEVER
  - Method is executed outside of a transaction. Throws exception if a transaction exists.

- NOT_SUPPORTED
  - Method is executed outside of a transaction. Suspends any existing transaction.

- REQUIRED
  - Method will be executed in the current transaction. If no transaction exists, one will be created.

- REQUIRES_NEW
  - Creates a new transaction in which the method will be executed. Suspends any existing transaction.

- SUPPORTS
  - Method will be executed in the current transaction, if one exists, or outside of a transaction if one does not exist.

|               | reuse the existing transaction           | create a new transaction | require transaction |
|---------------|------------------------------------------|--------------------------|---------------------|
| REQUIRED      | Yes                                      | Yes                      | Yes                 |
| NESTED        | Yes - create a nested one                | Yes                      | Yes                 |
| MANDATORY     | Yes - throw and exception if none exists | No                       | Yes                 |
| REQUIRES_NEW  | No  - suspend the current one            | Yes                      | Yes                 |
| SUPPORTS      | Yes                                      | No                       | No                  |
| NOT_SUPPORTED | No  - suspend the current one            | No                       | No                  |
| NEVER         | No  - throw an exception if one exists   | No                       | No                  |


#### Transaction Isolation

Transaction isolation in database systems determine how the changes within a transaction are visible to other users and systems accessing the database prior to the transaction being committed. A higher isolation level reduces, or even eliminates, the chance for the problems described below that may appear when the database is updated and accessed concurrently. The drawback of higher isolation levels is a reduction of the ability of multiple users and systems concurrently accessing the database as well as increased use of system resources on the database server.

|                  | dirty reads | non-repeatable reads | phantom reads |
|------------------|-------------|----------------------|---------------|
| READ_UNCOMMITTED | Yes         | Yes                  | Yes           |
| READ_COMMITTED   | No          | Yes                  | Yes           |
| REPEATABLE_READ  | No          | No                   | Yes           |
| SERIALIZABLE     | No          | No                   | No            |


Level of Read phenomana (from worse to better):

- `Dirty reads`: A dirty read (aka uncommitted dependency) occurs when a transaction is allowed to read data from a row that has been modified by another running transaction and not yet committed.

- `Non-repeatable reads`: A non-repeatable read occurs when, during the course of a transaction, a row is retrieved twice and the values within the row differ between reads.

- `Phantom reads`: A phantom read occurs when, in the course of a transaction, new rows are added or removed by another transaction to the records being read.

----------

### What happens if one `@Transactional` annotated method is calling another `@Transactional` annotated method inside a same object instance?

A self-invocation of a proxied Spring bean effectively bypasses the proxy and thus also any transaction interceptor managing transactions. Thus the second method, the method being invoked from another method in the bean, will execute in the same transaction context as the first. Any configuration in a `@Transactional` annotation on the second method will not come into effect.

If Spring transaction management is used with AspectJ, then any transaction-configuration using `@Transactional` on non-public methods will be honored.

----------

### Where can the `@Transactional` annotation be used? What is a typical usage if you put it at class level?

The `@Transactional` annotation can be used on class and method level both in classes and interfaces. When using Spring AOP proxies, only `@Transactional` annotations on public methods will have any effect – applying the` @Transactional` annotation to protected or private methods or methods with package visibility will not cause errors but will not give the desired transaction management.

The recommendation is to apply the `@Transactional` annotation only to methods with **public** visibility, this way, regardless of the type of proxy created, you’ll always get the transactional behavior where you expect it. Also, keep in mind local calls within the same class cannot be intercepted.

The `@Transactional` annotation can be used at the class level. In this case, all the public methods inherit the transactional behavior defined by the annotation on the class, but `@Transactional` annotations used at the method level, can override any transactional settings inherited from the class. Therefore, the most derived location takes precedence when evaluating the transactional settings for a method.

It is recommended and practical to annotate only **concrete** classes (and methods of **concrete** classes) with the `@Transactional` annotation.

The `@Transactional` annotation can be used on an interface (or an interface method), this requires you to use interface-based proxies; otherwise, if class-based proxies are used, the annotations are not inherited from the interface.

`@Transactional` can also be used on abstract classes as well, and in this case, whether the transactional behavior is applied depends on the type of proxy created.

----------

### What does declarative transaction management mean?

Declarative transaction management means that the methods which need to be executed in the context of a transaction and the transaction properties for these methods are declared, as opposed to implemented. This is accomplished using annotations (e.g. `@Transactional`) or Spring XML configuration.

----------

### What is the default rollback policy? How can you override it?

The default rollback policy of Spring transaction management is that automatic rollback only takes place in the case of an unchecked exception being thrown.

The types of exceptions that are to cause a rollback can be configured using the `rollbackFor` element of the `@Transactional` annotation. In addition, the types of exceptions that not are to cause rollbacks can also be configured using the `noRollbackFor` element.

----------

### What is the default rollback policy in a JUnit test, when you use the `@RunWith(SpringJUnit4ClassRunner.class)` in JUnit 4 or `@ExtendWith(SpringExtension. class)` in JUnit 5, and annotate your `@Test` annotated method with `@Transactional`?

If a test-method annotated with `@Test` is also annotated with `@Transactional`, then the test-method will be executed in a transaction. Such a transaction will automatically be rolled back after the completion of the test-method. The reason for this is so that test-methods should be able to either modify the state of any database themselves or invoke methods that modifies the state of the database and have such changes reverted after the completion of the test method.

The rollback policy of a test can be changed using the `@Rollback(false)` or `@Commit`.

----------

### Are you able to participate in a given transaction in Spring while working with JPA?

The short answer is: Yes.

The Spring `JpaTransactionManager` supports direct DataSource access within one and the same transaction allowing for mixing plain JDBC code that is unaware of JPA with code that use JPA.

If the Spring application is to be deployed to a JavaEE server, then `JtaTransactionManager` can be used in the Spring application. `JtaTransactionManager` will delegate to the JavaEE server’s transaction coordinator

----------

### Which `PlatformTransactionManager(s)` can you use with JPA?

First, any `JtaTransactionManager` can be used with JPA since JTA transactions are global transactions, that is they can span multiple resources such as databases, queues etc. Thus JPA persistence becomes just another of these resources that can be involved in a transaction.

When using JPA with one single entity manager factory, the Spring Framework `JpaTransactionManager` is the recommended choice. This is also the only transaction manager that is JPA entity manager factory aware.

If the application has multiple JPA entity manager factories that are to be transactional, then a JTA transaction manager is required.

----------

### What do you have to configure to use JPA with Spring? How does Spring Boot make this easier?

#### What do you have to configure to use JPA with Spring?

- Declare appropriate dependencies

- `@Entity` classes

- `EntityManagerFactory` bean

- `DataSource` bean

- `TransactionManager` bean (Typically `JpaTransactionManager` from the Spring Framework)

- Extends `Repository` interfaces

``` java
@Bean
public DataSource dataSource() {
    HikariConfig hikariConfig = new HikariConfig();
    hikariConfig.setDriverClassName(driverClassName);
    hikariConfig.setJdbcUrl(url);
    hikariConfig.setUsername(username);
    hikariConfig.setPassword(password);

    hikariConfig.setMaximumPoolSize(5);
    hikariConfig.setConnectionTestQuery("SELECT 1");
    hikariConfig.setPoolName("cemsPool");
    return new HikariDataSource(hikariConfig);
}

@Bean
public Properties hibernateProperties() {
    Properties hibernateProp = new Properties();
    hibernateProp.put("hibernate.dialect", dialect);
    hibernateProp.put("hibernate.hbm2ddl.auto", hbm2ddl);

    hibernateProp.put("hibernate.format_sql", true);
    hibernateProp.put("hibernate.use_sql_comments", true);
    hibernateProp.put("hibernate.show_sql", true);
    return hibernateProp;
}

@Bean
public EntityManagerFactory entityManagerFactory() {
    LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
    factoryBean.setPackagesToScan("com.apress.cems.dao");
    factoryBean.setDataSource(dataSource());
    factoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
    factoryBean.setJpaProperties(hibernateProperties());
    factoryBean.afterPropertiesSet();
    return factoryBean.getNativeEntityManagerFactory();
}

@Bean
public PlatformTransactionManager transactionManager() {
    return new JpaTransactionManager(entityManagerFactory());
}

public interface PersonRepo extends JpaRepository<Person, Long> {}

@Entity
public class Person {
    @Id
    protected Long id;
}
```

#### How does Spring Boot make this easier?

Spring Boot provides a starter module that:

- Provides a default set of dependencies needed for using JPA in a Spring application.

- Provides all the Spring beans needed to use JPA.
  - These beans can be easily customized by declaring bean(s) with the same name(s) in the application, as is standard in Spring applications.
  
- Provides a number of default properties related to persistence and JPA.
  - These properties can be easily customized by declaring one or more properties in the application properties-file supplying new values.
