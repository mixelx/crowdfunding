package com.course.startItProject.controller;

import com.course.startItProject.entity.*;
import com.course.startItProject.service.*;
import com.course.startItProject.entity.*;
import com.course.startItProject.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

@Controller
public class ProjectController {
    @Autowired
    private UserService userService;

    @Autowired
    private YouTubeService youTubeService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private CloudinaryService cloudinaryService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private BonusService bonusService;

    @Autowired
    private RatingService ratingService;

    @Autowired
    private SessionService sessionService;

    @Autowired
    private NewsService newsService;

    @Autowired
    private DonateService donateService;

    @GetMapping("")
    public String getFirstPage(ModelMap modelMap) {
        modelMap.addAttribute("listOfProject", projectService.findTop3ByRating());
        modelMap.addAttribute("news", newsService.get5latestNews());
        modelMap.addAttribute("donates", donateService.get5latestDonates());
        modelMap.addAttribute("profileImg", userService.getProfileUrlOfCurrentUser());
        return "startPage";
    }

    @PostMapping("/addCampaign")
    public String saveProject(@AuthenticationPrincipal User user, @ModelAttribute Project project, @RequestParam("file") MultipartFile[] file) {
        for (MultipartFile multipartFile : file) {
            imageService.saveImage(project, cloudinaryService.uploadFile(multipartFile));
        }
        project.setAuthor(user);
        String videoLink = project.getVideoLink();
        project.setVideoLink(youTubeService.adaptLink(videoLink));
        projectService.save(project);
        return ("redirect:/");
    }

    @PostMapping("/deleteProject")
    public String deleteProject(@RequestParam("id") Long projectId) {
        projectService.deleteProject(projectId);
        return "redirect:/";
    }

    @GetMapping("/project")
    public String getProjectPage(@RequestParam("id") long projectId, ModelMap model, Principal principal) {
        Project project = projectService.findProjectById(projectId);
        project.setUrls(imageService.getUrlsOfProject(project));
        model.addAttribute("rating", ratingService.findByProjectAndUser(project, sessionService.getCurrentUser(userService)));
        model.addAttribute("bonuses", bonusService.findByProject(project));
        model.addAttribute("project", project);
        model.addAttribute("profileImg", userService.getProfileUrlOfCurrentUser());
        return "project";
    }

    @GetMapping("/addCampaign")
    public String addCampaign() {
        return "createCampaign";
    }


    @GetMapping("/allCampaigns")
    public String getAllCampaigns(ModelMap modelmap) {
        List<Project> projects;
        projects = projectService.getAll();
        for (Project project : projects) {
            project.setUrls(imageService.getUrlsOfProject(project));
        }
        modelmap.addAttribute("profileImg", userService.getProfileUrlOfCurrentUser());
        modelmap.addAttribute("listOfProject", projects);
        return "allCampaigns";
    }

    @GetMapping("/filter")
    public String filterCampaigns(@RequestParam("categoryName") @ModelAttribute("category") Categories category, ModelMap model) {
        List<Project> filteredProjects = projectService.findByCategories(category);
        for (Project project : filteredProjects) {
            project.setUrls(imageService.getUrlsOfProject(project));
        }
        model.addAttribute("listOfProject", filteredProjects);
        model.addAttribute("profileImg", userService.getProfileUrlOfCurrentUser());
        return "allCampaigns";
    }

    @PostMapping("/editform")
    public String getEditForm(@RequestParam("id") long projectId, ModelMap modelMap) {
        modelMap.addAttribute("project", projectService.findProjectById(projectId));
        return "editform";
    }

    @PostMapping("/edit")
    public String editProject(@RequestParam("id") long projectId, @ModelAttribute("project") Project project) {
        Project projectToEdit = projectService.findProjectById(projectId);
        projectService.editProject(projectToEdit, project);
        projectService.save(projectToEdit);
        return "redirect:/project?id=" + projectId;
    }

    @PostMapping("addbonus")
    public String addBonus(@RequestParam("id") long projectId, @ModelAttribute("bonus") Bonus bonus) {
        bonus.setProject(projectService.findProjectById(projectId));
        bonusService.save(bonus);
        return "redirect:/project?id=" + projectId;
    }

    @PostMapping("rate")
    public String rate(@RequestParam("projectId") long projectId, @ModelAttribute("rating") Rating rating) {
        Project project = projectService.findProjectById(projectId);
        ratingService.save(rating, project, userService);
        project.setRating(ratingService.getAverageRating(project).intValue());
        projectService.save(project);
        return "redirect:/project?id=" + projectId;
    }

    @PostMapping("addnews")
    public String addNews(@RequestParam("projectId") long projectId, @RequestParam("file") MultipartFile image, @ModelAttribute("news") News news) {
        newsService.save(news, cloudinaryService.uploadFile(image), projectService.findProjectById(projectId));
        return "redirect:/project?id=" + projectId;
    }

    @PostMapping("newspage")
    public String newsPage(@RequestParam("projectId") long projectId, ModelMap modelMap) {
        modelMap.addAttribute("project", projectService.findProjectById(projectId));
        return "newspage";
    }
}

