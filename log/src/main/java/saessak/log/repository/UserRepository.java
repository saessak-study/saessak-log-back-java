package saessak.log.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import saessak.log.domain.user.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
