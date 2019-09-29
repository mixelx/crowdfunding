package com.angular.startItProject.repo;

import com.angular.startItProject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    User findById(long id);

    User findByEmail(String email);

}
