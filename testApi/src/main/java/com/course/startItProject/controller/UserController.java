package com.course.startItProject.controller;

import com.course.startItProject.entity.User;
import com.course.startItProject.service.impl.CloudinaryServiceImpl;
import com.course.startItProject.service.impl.HistoryServiceImpl;
import com.course.startItProject.service.impl.ProjectServiceImpl;
import com.course.startItProject.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class UserController {
    @Autowired
    private UserServiceImpl userServiceImpl;

    @Autowired
    private HistoryServiceImpl historyServiceImpl;

    @Autowired
    private ProjectServiceImpl projectServiceImpl;

    @Autowired
    private CloudinaryServiceImpl cloudinaryServiceImpl;

    @RequestMapping("/user")
    public String userProfile(@RequestParam("id") long userId, ModelMap modelMap) {
        User user = userServiceImpl.findById(userId);
        modelMap.addAttribute("user", user);
        modelMap.addAttribute("projects", projectServiceImpl.findByAuthor(user));
        modelMap.addAttribute("myprojects", projectServiceImpl.getMyProjects());
        modelMap.addAttribute("profileImg", userServiceImpl.getProfileUrlOfCurrentUser());
        if (user.getUsername().equals(SecurityContextHolder.getContext().getAuthentication().getName())) {
            return "myprofile";
        }
        return "user";
    }

    @GetMapping("/profile")
    public String profile(ModelMap modelMap) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userServiceImpl.findByUsername(name);
        modelMap.addAttribute("user", user);
        modelMap.addAttribute("myprojects", projectServiceImpl.getMyProjects());
        modelMap.addAttribute("histories", historyServiceImpl.findByUser(user));
        modelMap.addAttribute("profileImg", userServiceImpl.getProfileUrlOfCurrentUser());
        return "myprofile";
    }

    @PostMapping("/updatePic")
    public String updatePic(@RequestParam("userName") @ModelAttribute long userId, @RequestParam("file") MultipartFile file) {
        User user = userServiceImpl.findById(userId);
        user.setUrl(cloudinaryServiceImpl.uploadFile(file));
        userServiceImpl.save(user);
        return "redirect:/profile";
    }
}
