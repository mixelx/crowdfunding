package com.course.startItProject.service.impl;


import com.course.startItProject.entity.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SessionServiceImpl {
    public User getCurrentUser(UserServiceImpl userServiceImpl) {
        return userServiceImpl.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    public void expireUsersSessionById(long userId, UserServiceImpl userServiceImpl, SessionRegistry sessionRegistry) {
        List<Object> principals = sessionRegistry.getAllPrincipals();
        String username = userServiceImpl.findById(userId).getUsername();
        User userToDelete = new User();
        for (Object principal : principals) {
            User temp = (User) principal;
            if (temp.getUsername().equals(username)) {
                userToDelete = temp;
            }
        }
        if (userToDelete.getId() != null) {
            sessionRegistry.getAllSessions(userToDelete, false).get(0).expireNow();
        }
    }
}
