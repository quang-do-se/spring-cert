
### What is a Spring Data Repository interface?

A repository interface, also known as a Spring Data repository, is a repository that need no implementation and that supports the basic CRUD (create, read, update and delete) operations. Such a repository is declared as an interface that typically extend the Repository interface or an interface extending the Repository interface. The Repository uses Java generics and takes two type arguments; an entity type and a type of the primary key of entities.

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

----------

### How are Spring Data repositories implemented by Spring at runtime?

----------

### What is` @Query` used for?
