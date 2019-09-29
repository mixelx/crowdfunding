package com.course.startItProject.repo;

import com.course.startItProject.entity.Image;
import com.course.startItProject.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> findByProject(Project project);
}
