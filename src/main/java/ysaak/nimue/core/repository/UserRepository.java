package ysaak.nimue.core.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ysaak.nimue.core.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User findByLogin(String login);

    User findByEmail(String email);
}
