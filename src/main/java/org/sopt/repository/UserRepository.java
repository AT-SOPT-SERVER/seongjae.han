package org.sopt.repository;

import java.util.Optional;
import org.sopt.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

   Optional<User> findById(final Long id);
}
