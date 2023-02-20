package saessak.log.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import saessak.log.user.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByProfileId(String profileId);
    User findByName(String name);
    User findByEmail(String email);

    @Query("select u from User u where u.profileId = :profileId")
    Optional<User> findOptionalByProfileId(@Param("profileId") String profileId);
}
