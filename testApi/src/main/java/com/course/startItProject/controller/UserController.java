package com.course.startItProject.controller;

import com.course.startItProject.entity.User;
import com.course.startItProject.service.CloudinaryService;
import com.course.startItProject.service.HistoryService;
import com.course.startItProject.service.ProjectService;
import com.course.startItProject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private HistoryService historyService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private CloudinaryService cloudinaryService;

    @RequestMapping("/user")
    public String userProfile(@RequestParam("id") long userId, ModelMap modelMap) {
        User user = userService.findById(userId);
        modelMap.addAttribute("user", user);
        modelMap.addAttribute("projects", projectService.findByAuthor(user));
        modelMap.addAttribute("myprojects", projectService.getMyProjects());
        modelMap.addAttribute("profileImg", userService.getProfileUrlOfCurrentUser());
        if (user.getUsername().equals(SecurityContextHolder.getContext().getAuthentication().getName())) {
            return "myprofile";
        }
        return "user";
    }

    @GetMapping("/profile")
    public String profile(ModelMap modelMap) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByUsername(name);
        modelMap.addAttribute("user", user);
        modelMap.addAttribute("myprojects", projectService.getMyProjects());
        modelMap.addAttribute("histories", historyService.findByUser(user));
        modelMap.addAttribute("profileImg", userService.getProfileUrlOfCurrentUser());
        return "myprofile";
    }

    @PostMapping("/updatePic")
    public String updatePic(@RequestParam("userName") @ModelAttribute long userId, @RequestParam("file") MultipartFile file) {
        User user = userService.findById(userId);
        user.setUrl(cloudinaryService.uploadFile(file));
        userService.save(user);
        return "redirect:/profile";
    }
}
