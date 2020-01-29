package com.course.startItProject.controller;

import com.course.startItProject.entity.User;
import com.course.startItProject.roles.Role;
import com.course.startItProject.service.impl.ProjectServiceImpl;
import com.course.startItProject.service.impl.RolesServiceImpl;
import com.course.startItProject.service.impl.SessionServiceImpl;
import com.course.startItProject.service.impl.UserServiceImpl;
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
    private UserServiceImpl userServiceImpl;

    @Autowired
    ProjectServiceImpl projectServiceImpl;

    @Autowired
    SessionRegistry sessionRegistry;

    @Autowired
    SessionServiceImpl sessionServiceImpl;

    @Autowired
    RolesServiceImpl rolesServiceImpl;

    @RequestMapping("/admin")
    public String adminPage(ModelMap modelMap) {
        modelMap.addAttribute("users", userServiceImpl.findAll());
        modelMap.addAttribute("profileImg", userServiceImpl.getProfileUrlOfCurrentUser());
        return "admin";
    }

    @PostMapping("/deleteUser")
    public String deleteUser(@RequestParam("id") long userId) {
        projectServiceImpl.deleteListOfProjects(projectServiceImpl.findByAuthor(userServiceImpl.findById(userId)));
        sessionServiceImpl.expireUsersSessionById(userId, userServiceImpl, sessionRegistry);
        userServiceImpl.delete(userServiceImpl.findById(userId));
        return "redirect:/admin";
    }

    @PostMapping("/blockUser")
    public String blockUser(@RequestParam("id") long userId) {
        sessionServiceImpl.expireUsersSessionById(userId, userServiceImpl, sessionRegistry);
        User userToBlock = userServiceImpl.findById(userId);
        userToBlock.setActive(false);
        userServiceImpl.save(userToBlock);
        return "redirect:/admin";
    }

    @PostMapping("/unblockUser")
    public String unblockUser(@RequestParam("id") long userId) {
        User userToUnblock = userServiceImpl.findById(userId);
        userToUnblock.setActive(true);
        userServiceImpl.save(userToUnblock);
        return "redirect:/admin";
    }

    @PostMapping("/changeRole")
    public String changeRole(@RequestParam("id") long userId) {
        User user = userServiceImpl.findById(userId);
        Object[] rolearray = user.getRoles().toArray();
        if (Role.ADMIN.equals(rolearray[0])) {
            rolesServiceImpl.changeRoleToUser(user);
        } else {
            rolesServiceImpl.changeRoleToAdmin(user);
        }
        userServiceImpl.save(user);
        return "redirect:/admin";
    }
}
