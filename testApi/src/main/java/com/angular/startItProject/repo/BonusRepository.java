package com.angular.startItProject.repo;

import com.angular.startItProject.entity.Bonus;
import com.angular.startItProject.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BonusRepository extends JpaRepository<Bonus, Long> {
    List<Bonus> findByProject(Project project);

    Bonus findById(long id);


}
