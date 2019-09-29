package com.course.startItProject.repo;

import com.course.startItProject.entity.Categories;
import com.course.startItProject.entity.Project;
import com.course.startItProject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findByAuthor(User user);

    List<Project> findByCategories(Categories categories);

    Project findProjectById(long id);

    List<Project> findTop3ByOrderByRatingDesc();
}
