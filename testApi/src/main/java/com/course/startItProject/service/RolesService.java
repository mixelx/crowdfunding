package com.course.startItProject.service;

import com.course.startItProject.entity.User;
import com.course.startItProject.roles.Role;
import org.springframework.stereotype.Service;

@Service
public class RolesService {
    public void changeRoleToAdmin(User user) {
        user.getRoles().clear();
        user.getRoles().add(Role.ADMIN);
    }

    public void changeRoleToUser(User user) {
        user.getRoles().clear();
        user.getRoles().add(Role.USER);
    }
}
