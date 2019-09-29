package com.course.startItProject.service;

import com.course.startItProject.entity.User;
import com.course.startItProject.repo.UserRepository;
import com.course.startItProject.roles.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private SessionService sessionService;

    @Autowired
    private UserService userService;

    @Autowired
    private EmailSenderService emailSenderService;

    @Autowired
    private ConfirmationTokenService confirmationTokenService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void saveUser(User user) {
        user.setRoles(Collections.singleton(Role.USER));
        user.setUrl("https://res.cloudinary.com/dtr6qm7uy/image/upload/v1569767467/355-3553881_stockvader-predicted-adig-user-profile-icon-png-clipart_tv0vik.jpg");
        user.setActive(false);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        confirmationTokenService.sendVerificationToken(user, emailSenderService);
    }

    public String getProfileUrlOfCurrentUser() {
        User user = sessionService.getCurrentUser(userService);
        if (user != null) {
            return user.getUrl();
        }
        return "";
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(long userId) {
        return userRepository.findById(userId);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }

    public User findByUsername(String name) {
        return userRepository.findByUsername(name);
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public void delete(User user) {
        userRepository.delete(user);
    }

    User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

}
