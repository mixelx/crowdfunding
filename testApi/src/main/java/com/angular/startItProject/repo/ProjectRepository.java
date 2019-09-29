package com.angular.startItProject.repo;

import com.angular.startItProject.entity.Categories;
import com.angular.startItProject.entity.Project;
import com.angular.startItProject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findByAuthor(User user);

    List<Project> findByCategories(Categories categories);

    Project findProjectById(long id);

    List<Project> findTop3ByOrderByRatingDesc();
}
