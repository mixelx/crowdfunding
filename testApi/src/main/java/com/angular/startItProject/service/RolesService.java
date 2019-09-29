package com.angular.startItProject.service;

import com.angular.startItProject.entity.User;
import com.angular.startItProject.roles.Role;
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
