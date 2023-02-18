package saessak.log.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import saessak.log.user.domain.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByProfileId(String profileId);
}
