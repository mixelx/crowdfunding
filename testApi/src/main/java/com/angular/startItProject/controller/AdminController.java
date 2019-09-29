package com.angular.startItProject.controller;

import com.angular.startItProject.entity.User;
import com.angular.startItProject.roles.Role;
import com.angular.startItProject.service.ProjectService;
import com.angular.startItProject.service.RolesService;
import com.angular.startItProject.service.SessionService;
import com.angular.startItProject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    @Autowired
    private UserService userService;

    @Autowired
    ProjectService projectService;

    @Autowired
    SessionRegistry sessionRegistry;

    @Autowired
    SessionService sessionService;

    @Autowired
    RolesService rolesService;

    @RequestMapping("/admin")
    public String adminPage(ModelMap modelMap) {
        modelMap.addAttribute("users", userService.findAll());
        modelMap.addAttribute("profileImg", userService.getProfileUrlOfCurrentUser());
        return "admin";
    }

    @PostMapping("/deleteUser")
    public String deleteUser(@RequestParam("id") long userId) {
        projectService.deleteListOfProjects(projectService.findByAuthor(userService.findById(userId)));
        sessionService.expireUsersSessionById(userId, userService, sessionRegistry);
        userService.delete(userService.findById(userId));
        return "redirect:/admin";
    }

    @PostMapping("/blockUser")
    public String blockUser(@RequestParam("id") long userId) {
        sessionService.expireUsersSessionById(userId,userService, sessionRegistry);
        User userToBlock = userService.findById(userId);
        userToBlock.setActive(false);
        userService.save(userToBlock);
        return "redirect:/admin";
    }

    @PostMapping("/unblockUser")
    public String unblockUser(@RequestParam("id") long userId) {
        User userToUnblock = userService.findById(userId);
        userToUnblock.setActive(true);
        userService.save(userToUnblock);
        return "redirect:/admin";
    }

    @PostMapping("/changeRole")
    public String changeRole(@RequestParam("id") long userId) {
        User user = userService.findById(userId);
        Object[] rolearray = user.getRoles().toArray();
        if (Role.ADMIN.equals(rolearray[0])) {
            rolesService.changeRoleToUser(user);
        } else {
            rolesService.changeRoleToAdmin(user);
        }
        userService.save(user);
        return "redirect:/admin";
    }
}
