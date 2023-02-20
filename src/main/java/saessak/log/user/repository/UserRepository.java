package saessak.log.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import saessak.log.user.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByProfileId(String profileId);

}
