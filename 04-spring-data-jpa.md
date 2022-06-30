<!-- markdown-toc start - Don't edit this section. Run M-x markdown-toc-refresh-toc -->
**Table of Contents**

- [What is a Spring Data Repository interface?](#what-is-a-spring-data-repository-interface)
- [How do you define a Spring Data Repository interface? Why is it an interface not a class?](#how-do-you-define-a-spring-data-repository-interface-why-is-it-an-interface-not-a-class)
- [What is the naming convention for finder methods in a Spring Data Repository interface?](#what-is-the-naming-convention-for-finder-methods-in-a-spring-data-repository-interface)
- [How are Spring Data repositories implemented by Spring at runtime?](#how-are-spring-data-repositories-implemented-by-spring-at-runtime)
- [What is `@Query` used for?](#what-is-query-used-for)

<!-- markdown-toc end -->

----------

### What is a Spring Data Repository interface?

A repository interface, also known as a Spring Data repository, is a repository that need no implementation and that supports the basic CRUD (create, read, update and delete) operations. Such a repository is declared as an interface that typically extend the Repository interface or an interface extending the Repository interface. The Repository uses Java generics and takes two type arguments; an entity type and a type of the primary key of entities: `Repository<T, ID extends Serializable>`.

There are 4 interfaces you can extend:

- `Repository`
- `CrudRepository`
- `PagingAndSortingRepository`
- `JpaRepository`

You can also annotate your repository class with `@RepositoryDefinition`. This will have the same effect as extending the `Repository` interface.

----------

### How do you define a Spring Data Repository interface? Why is it an interface not a class?

- Simply extends JpaRepository interface and Spring will implement the interface and add data functionalities (CRUD, pagination...) at runtime (using JDK proxy).


``` java
@Entity
public class Person {
  @Id
  private Long id;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }
}

public interface PersonRepo extends JpaRepository<Person, Long> {}

@Configuration
@componentScan(basePackages = {"com.myapp"})
@EnableJpaRepositories(basePackages = {"com.myapp"})
public class AppConfig{}
```

----------

### What is the naming convention for finder methods in a Spring Data Repository interface?

As earlier, it is possible to add custom finder methods to Spring Data repository interfaces. If following the naming convention below, Spring Data will recognize these find methods and supply an implementation for these methods. The naming convention of these finder methods are:

**`find[Distinct](First|Top[count])By[PropertyExpression][ComparisonOperator:=][OrderingOperator]`**

- Finder method names always start with `find`.

- Optionally, `First` or `Top` can be added after `find` in order to retrieve only the first found entity. 
  - When retrieving only one single entity, the finder method will return null if no matching entity is found. Alternatively the return-type of the method can be declared to use the Java `Optional` wrapper to indicate that a result may be absent.
  - If a count is supplied after the `First`, for example `findFirst10`, then the count number of entities first found will be the result.
  
- The optional `PropertyExpression` selects the property of a managed entity that will be used to select the entity/entities that are to be retrieved.
  - Properties may be traversed, in which case underscore can be added to separate names of nested properties to avoid disambiguities. If the property to be examined is a string type, then `IgnoreCase` may be added after the property name in order to perform case-insensitive comparison. Multiple property expressions can be chained using `AND` or `OR`.
  
- The optional `ComparisonOperator` enables creation of finder methods that selects a range of entities. Some comparison operators available are: `LessThan`, `GreaterThan`, `Between`, `Like`. If this is omitted, it means `Equals` or `Is`.

- Finally the optional `OrderingOperator` allows for ordering a list of multiple entities on a property in the entity. This is accomplished by adding `OrderBy`+ `a Property Expression` + `Asc` or `Desc`.

- Example: **`findPersonByLastnameOrderBySocialSecurityNumberDesc`** – find persons that have a supplied last name and order them in descending order by social security number.

- `find`, `read`, `get`, `query`, `search`  are aliases. They will work the same.
  - Reference: https://github.com/spring-projects/spring-data-commons/blob/main/src/main/java/org/springframework/data/repository/query/parser/PartTree.java#L60
  - There is `count` as well.

Reference: https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.query-creation

----------

### How are Spring Data repositories implemented by Spring at runtime?

For a Spring Data repository a `JDK dynamic proxy` is created which intercepts all calls to the repository. The default behavior is to route calls to the default repository implementation, which in Spring Data JPA is the `SimpleJpaRepository` class. It is possible to customize either the implementation of one specific repository type or customize the implementation used for all repositories.

In the former case, customization of one repository type, the proxy for the particular type will invoke any method(s) implemented in the custom implementation, or, if no method exist in the custom implementation, invoke the default method. 

In the latter case, customization applied to all repository types, the proxies for repositories will invoke any method(s) implemented in the custom implementation, or, if no method exist in the custom implementation, invoke the default method.

----------

### What is `@Query` used for?

The `@Query` annotation allows for specifying a query to be used with a Spring Data JPA repository method. This allows for customizing the query used for the annotated repository method or supplying a query that is to be used for a repository method that do not adhere to the finder method naming convention described earlier.

- Define a custom query to execute in repository.
- Can execute both JPQL and native SQL queries.
- Queries annotated to the `@Query` method take precedence over queries defined using `@NamedQuery` or named queries declared in _orm.xml_.
- `@NamedNativeQuery` is used to define the query in native SQL but losing the database platform independence.
- Use attribute `nativeQuery = true` to write native SQL.
- `@Query` supports **`SpEL`**.
- It has a Target of type ANNOTATION_TYPE and METHOD, thus it can be used to create custom annotations as well, as on top of methods.
- Spring Data JPA does not currently support dynamic sorting for native queries, because it would have to manipulate the actual query declared, which it cannot do reliably for native SQL. You can, however, use native queries for pagination by specifying the count query yourself, as shown in the following example:

``` java
public interface PersonRepository extends JpaRepository<Person, Long> {
  // Native Query
  @Query(value = "SELECT * FROM Person WHERE lastName = ?1",
         countQuery = "SELECT count(*) FROM Person WHERE lastName = ?1",
         nativeQuery = true)
  Page<User> findByLastname(String lastname, Pageable pageable);

  // JPQL
  @Query("select p from Person p where p.username like %?1%")
  Optional<Person> findByUsername(String username)

  // JPQL
  @Query("select p from Person p where p.firstName=:fn and p.lastName=:ln")
  Optional<Person> findByCompleteName(@Param("fn") String firstName,
                                      @Param("ln") String lastName)
}
```

Reference: https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.at-query.native
