What is the difference between checked and unchecked exceptions?

----------

How do you configure a DataSource in Spring?

----------

What is the Template design pattern and what is the JDBC template?

----------

What is a callback? What are the JdbcTemplate callback interfaces that can be used with queries? What is each
used for? (You would not have to remember the interface names in the exam, but you should know what they
do if you see them in a code sample).

----------

Can you execute a plain SQL statement with the JDBC template?

----------

When does the JDBC template acquire (and release) a connection, for every method called or once per
template? Why?

----------

How does the JdbcTemplate support queries? How does it return objects and lists/maps of objects?

----------

What is a transaction? What is the difference between a local and a global transaction?

----------

Is a transaction a cross cutting concern? How is it implemented by Spring?

----------

How are you going to define a transaction in Spring?

----------

Is the JDBC template able to participate in an existing transaction?

----------

What is @EnableTransactionManagement for?

----------

How does transaction propagation work?

----------

What happens if one @Transactional annotated method is calling another @Transactional annotated method
inside a same object instance?

----------

Where can the @Transactional annotation be used? What is a typical usage if you put it at class level?

----------

What does declarative transaction management mean?

----------

What is the default rollback policy? How can you override it?

----------

What is the default rollback policy in a JUnit test, when you use the @
RunWith(SpringJUnit4ClassRunner.class) in JUnit 4 or @ExtendWith(SpringExtension. class) in JUnit 5, and
annotate your @Test annotated method with @Transactional?

----------

Are you able to participate in a given transaction in Spring while working with JPA?

----------

Which PlatformTransactionManager(s) can you use with JPA?

----------

What do you have to configure to use JPA with Spring? How does Spring Boot make this easier
