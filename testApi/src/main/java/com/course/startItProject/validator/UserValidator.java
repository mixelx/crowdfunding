package com.course.startItProject.validator;

import com.course.startItProject.entity.User;
import com.course.startItProject.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {
    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "NotEmpty", "This field is required.");
        if (userRepository.findByUsername(user.getUsername()) != null) {
            errors.rejectValue("username", "Duplicate.userForm.username", "Someone already has that username.");
        }
        if (user.getUsername().length() < 4 || user.getUsername().length() > 16) {
            errors.rejectValue("username", "Size.userForm.username", "Please use between 4 and 16 characters.");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty", "This field is required.");
        if (!user.getConfirmPassword().equals(user.getPassword())) {
            errors.rejectValue("confirmPassword", "Diff.userForm.confirmPassword", "These passwords don't match.");
        }
        if (user.getPassword().length() < 6 || user.getPassword().length() > 16) {
            errors.rejectValue("password", "Size.userForm.password", "Try one between 6 and 16 characters.");
        }
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "NotEmpty", "This field is required.");
        if(userRepository.findByEmail(user.getEmail()) != null){
            errors.rejectValue("email", "Duplicate.userForm.email", "This email already exist.");
        }
    }
}
