package com.angular.startItProject.repo;

import com.angular.startItProject.entity.Project;
import com.angular.startItProject.entity.Rating;
import com.angular.startItProject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {

    List<Rating> findByProject(Project project);
    Rating findByProjectAndUser(Project project, User user);

}
