package genesys.project.usermanagement.repos;

import genesys.project.usermanagement.entities.User;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

/**
 * Repository interface for {@link User} entity.
 * This interface extends CrudRepository to provide basic CRUD operations.
 */
@EnableScan
public interface UserRepository extends CrudRepository<User, String> {
}
