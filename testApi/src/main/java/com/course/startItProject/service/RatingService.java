package com.course.startItProject.service;

import com.angular.startItProject.entity.*;
import com.course.startItProject.entity.Project;
import com.course.startItProject.entity.Rating;
import com.course.startItProject.entity.User;
import com.course.startItProject.repo.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RatingService {
    @Autowired
    private RatingRepository ratingRepository;
    @Autowired
    private SessionService sessionService;

    public void save(Rating rating, Project project, UserService userService){
        rating.setProject(project);
        rating.setUser(sessionService.getCurrentUser(userService));
        ratingRepository.save(rating);
    }

    public List<Rating> findByProject(Project project){
        return ratingRepository.findByProject(project);
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
        List<Integer> values = ratingRepository.findByProject(project)
                .stream()
                .map(Rating::getValue)
                .collect(Collectors.toList());
        return values
                .stream()
                .mapToDouble(a -> a)
                .average()
                .orElse(0.0);
    }

}
