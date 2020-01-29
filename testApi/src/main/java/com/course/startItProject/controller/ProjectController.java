package com.course.startItProject.controller;

import com.course.startItProject.entity.*;
import com.course.startItProject.service.impl.BonusServiceImpl;
import com.course.startItProject.service.impl.CloudinaryServiceImpl;
import com.course.startItProject.service.impl.DonateServiceImpl;
import com.course.startItProject.service.impl.ImageServiceImpl;
import com.course.startItProject.service.impl.NewsServiceImpl;
import com.course.startItProject.service.impl.ProjectServiceImpl;
import com.course.startItProject.service.impl.RatingServiceImpl;
import com.course.startItProject.service.impl.SessionServiceImpl;
import com.course.startItProject.service.impl.UserServiceImpl;
import com.course.startItProject.service.impl.YouTubeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

import static java.util.Arrays.stream;

@Controller
public class ProjectController {
    @Autowired
    private UserServiceImpl userServiceImpl;

    @Autowired
    private YouTubeServiceImpl youTubeService;

    @Autowired
    private ProjectServiceImpl projectServiceImpl;

    @Autowired
    private CloudinaryServiceImpl cloudinaryServiceImpl;

    @Autowired
    private ImageServiceImpl imageServiceImpl;

    @Autowired
    private BonusServiceImpl bonusServiceImpl;

    @Autowired
    private RatingServiceImpl ratingServiceImpl;

    @Autowired
    private SessionServiceImpl sessionServiceImpl;

    @Autowired
    private NewsServiceImpl newsServiceImpl;

    @Autowired
    private DonateServiceImpl donateServiceImpl;

    @GetMapping("")
    public String getFirstPage(ModelMap modelMap) {
        modelMap.addAttribute("listOfProject", projectServiceImpl.findTop3ByRating());
        modelMap.addAttribute("news", newsServiceImpl.get5latestNews());
        modelMap.addAttribute("donates", donateServiceImpl.get5latestDonates());
        modelMap.addAttribute("profileImg", userServiceImpl.getProfileUrlOfCurrentUser());
        return "startPage";
    }

    @PostMapping("/addCampaign")
    public String saveProject(@AuthenticationPrincipal User user, @ModelAttribute Project project, @RequestParam("file") MultipartFile[] file) {
        stream(file).forEach(multipartFile -> imageServiceImpl.saveImage(project, cloudinaryServiceImpl.uploadFile(multipartFile)));
        project.setAuthor(user);
        String videoLink = project.getVideoLink();
        project.setVideoLink(youTubeService.adaptLink(videoLink));
        projectServiceImpl.save(project);
        return ("redirect:/");
    }

    @PostMapping("/deleteProject")
    public String deleteProject(@RequestParam("id") Long projectId) {
        projectServiceImpl.deleteProject(projectId);
        return "redirect:/";
    }

    @GetMapping("/project")
    public String getProjectPage(@RequestParam("id") long projectId, ModelMap model, Principal principal) {
        Project project = projectServiceImpl.findProjectById(projectId);
        project.setImageUrls(imageServiceImpl.getUrlsOfProject(project));
        model.addAttribute("rating", ratingServiceImpl.findByProjectAndUser(project, sessionServiceImpl.getCurrentUser(userServiceImpl)));
        model.addAttribute("bonuses", bonusServiceImpl.findByProject(project));
        model.addAttribute("project", project);
        model.addAttribute("profileImg", userServiceImpl.getProfileUrlOfCurrentUser());
        return "project";
    }

    @GetMapping("/addCampaign")
    public String addCampaign() {
        return "createCampaign";
    }


    @GetMapping("/allCampaigns")
    public String getAllCampaigns(ModelMap modelmap) {
        List<Project> projects = projectServiceImpl.getAll();
        modelmap.addAttribute("profileImg", userServiceImpl.getProfileUrlOfCurrentUser());
        modelmap.addAttribute("listOfProject", projects);
        return "allCampaigns";
    }

    @GetMapping("/filter")
    public String filterCampaigns(@RequestParam("categoryName") @ModelAttribute("category") Categories category, ModelMap model) {
        List<Project> filteredProjects = projectServiceImpl.findByCategories(category);
        for (Project project : filteredProjects) {
            project.setImageUrls(imageServiceImpl.getUrlsOfProject(project));
        }
        model.addAttribute("listOfProject", filteredProjects);
        model.addAttribute("profileImg", userServiceImpl.getProfileUrlOfCurrentUser());
        return "allCampaigns";
    }

    @PostMapping("/editform")
    public String getEditForm(@RequestParam("id") long projectId, ModelMap modelMap) {
        modelMap.addAttribute("project", projectServiceImpl.findProjectById(projectId));
        return "editform";
    }

    @PostMapping("/edit")
    public String editProject(@RequestParam("id") long projectId, @ModelAttribute("project") Project project) {
        Project projectToEdit = projectServiceImpl.findProjectById(projectId);
        projectServiceImpl.editProject(projectToEdit, project);
        projectServiceImpl.save(projectToEdit);
        return "redirect:/project?id=" + projectId;
    }

    @PostMapping("addbonus")
    public String addBonus(@RequestParam("id") long projectId, @ModelAttribute("bonus") Bonus bonus) {
        bonus.setProject(projectServiceImpl.findProjectById(projectId));
        bonusServiceImpl.save(bonus);
        return "redirect:/project?id=" + projectId;
    }

    @PostMapping("rate")
    public String rate(@RequestParam("projectId") long projectId, @ModelAttribute("rating") Rating rating) {
        Project project = projectServiceImpl.findProjectById(projectId);
        ratingServiceImpl.save(rating, project, userServiceImpl);
        project.setRating(ratingServiceImpl.getAverageRating(project).intValue());
        projectServiceImpl.save(project);
        return "redirect:/project?id=" + projectId;
    }

    @PostMapping("addnews")
    public String addNews(@RequestParam("projectId") long projectId, @RequestParam("file") MultipartFile image, @ModelAttribute("news") News news) {
        newsServiceImpl.save(news, cloudinaryServiceImpl.uploadFile(image), projectServiceImpl.findProjectById(projectId));
        return "redirect:/project?id=" + projectId;
    }

    @PostMapping("newspage")
    public String newsPage(@RequestParam("projectId") long projectId, ModelMap modelMap) {
        modelMap.addAttribute("project", projectServiceImpl.findProjectById(projectId));
        return "newspage";
    }
}

