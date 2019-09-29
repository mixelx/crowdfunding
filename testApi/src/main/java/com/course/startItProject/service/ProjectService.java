package com.course.startItProject.service;

import com.course.startItProject.entity.Categories;
import com.course.startItProject.entity.Project;
import com.course.startItProject.entity.User;
import com.course.startItProject.repo.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {
    @Autowired
    private ImageService imageService;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserService userService;

    public List<Project> getAll() {
        return projectRepository.findAll();
    }

    public List<Project> findByAuthor(User user) {
        return projectRepository.findByAuthor(user);
    }

    public Project findProjectById(long projectId) {
        return projectRepository.findProjectById(projectId);
    }

    public List<Project> findByCategories(Categories category) {
        return projectRepository.findByCategories(category);
    }

    public List<Project> findTop3ByRating(){
        List<Project> topProjects = projectRepository.findTop3ByOrderByRatingDesc();
        for (Project project : topProjects) {
            project.setUrls(imageService.getUrlsOfProject(project));
        }
        return topProjects;
    }

    public void deleteProject(Long id) {
        projectRepository.deleteById(id);
    }

    public void save(Project project) {
        projectRepository.save(project);
    }

    public List<Project> getMyProjects() {
        User user = userService.findByUsername(SecurityContextHolder
                .getContext().getAuthentication().getName());
        return projectRepository.findByAuthor(user);
    }

    public void editProject(Project projectToEdit, Project project) {
        projectToEdit.setProjectName(project.getProjectName());
        projectToEdit.setCategories(project.getCategories());
        projectToEdit.setShortDisc(project.getShortDisc());
        projectToEdit.setGoal(project.getGoal());
        projectToEdit.setVideoLink(project.getVideoLink());
        projectToEdit.setDurationDate(project.getDurationDate());
        projectToEdit.setFullDisc(project.getFullDisc());
    }

    public void deleteListOfProjects(List<Project> projects) {
        for (Project project : projects) {
            projectRepository.delete(project);
        }
    }
}
