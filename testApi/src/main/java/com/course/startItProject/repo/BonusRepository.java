package com.course.startItProject.repo;

import com.course.startItProject.entity.Bonus;
import com.course.startItProject.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BonusRepository extends JpaRepository<Bonus, Long> {
    List<Bonus> findByProject(Project project);

    Bonus findById(long id);


}
