package com.course.startItProject.service.impl;

import com.course.startItProject.entity.Categories;
import com.course.startItProject.entity.Project;
import com.course.startItProject.entity.User;
import com.course.startItProject.repo.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.springframework.security.core.context.SecurityContextHolder.getContext;

@Service
public class ProjectServiceImpl {

    @Autowired
    private ImageServiceImpl imageServiceImpl;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private UserServiceImpl userServiceImpl;

    public List<Project> getAll() {
        return projectRepository.findAll()
                .stream()
                .peek(project -> project.setImageUrls(imageServiceImpl.getUrlsOfProject(project)))
                .collect(toList());
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
        return projectRepository.findTop3ByOrderByRatingDesc()
                .stream()
                .peek(project -> project.setImageUrls(imageServiceImpl.getUrlsOfProject(project)))
                .collect(toList());
    }

    public void deleteProject(Long id) {
        projectRepository.deleteById(id);
    }

    public void save(Project project) {
        projectRepository.save(project);
    }

    public List<Project> getMyProjects() {
        return projectRepository.findByAuthor(userServiceImpl.findByUsername(getContext().getAuthentication().getName()));
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
        projects.forEach(project -> projectRepository.delete(project));
    }
}
