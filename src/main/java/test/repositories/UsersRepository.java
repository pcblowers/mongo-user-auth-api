package test.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import test.models.Users;

public interface UsersRepository extends MongoRepository<Users, String> {
    Users findByEmail(String email);
    Users findByUsername(String username);
}
