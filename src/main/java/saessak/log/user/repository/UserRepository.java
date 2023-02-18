package saessak.log.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import saessak.log.user.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByProfileId(String profileId);

}
