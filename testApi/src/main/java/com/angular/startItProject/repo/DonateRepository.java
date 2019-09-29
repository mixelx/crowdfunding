package com.angular.startItProject.repo;

import com.angular.startItProject.entity.Donate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DonateRepository extends JpaRepository<Donate, Long> {
    List<Donate> findById(long id);

    List<Donate> findFirst5ByOrderByIdDesc();
}
