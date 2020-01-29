package com.course.startItProject.service.impl;

import com.course.startItProject.entity.Project;
import com.course.startItProject.entity.Rating;
import com.course.startItProject.entity.User;
import com.course.startItProject.repo.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RatingServiceImpl {
    @Autowired
    private RatingRepository ratingRepository;
    @Autowired
    private SessionServiceImpl sessionServiceImpl;

    public void save(Rating rating, Project project, UserServiceImpl userServiceImpl){
        rating.setProject(project);
        rating.setUser(sessionServiceImpl.getCurrentUser(userServiceImpl));
        ratingRepository.save(rating);
    }

    public Rating findByProjectAndUser(Project project, User user){
        try {
           return ratingRepository.findByProjectAndUser(project ,user);
        }
        catch (NullPointerException e){
            return null;
        }
    }

    public Double getAverageRating(Project project){
        return  ratingRepository.findByProject(project)
                .stream()
                .map(Rating::getValue)
                .mapToDouble(value -> value)
                .average()
                .orElse(0.0);
    }

}
