package com.course.startItProject.repo;

import com.course.startItProject.entity.News;
import com.course.startItProject.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsRepository extends JpaRepository<News, Long > {

    List<News> findByProject(Project project);

    List<News> findFirst5ByOrderByIdDesc();
}
