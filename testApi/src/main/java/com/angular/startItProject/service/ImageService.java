package com.angular.startItProject.service;

import com.angular.startItProject.entity.Image;
import com.angular.startItProject.entity.Project;
import com.angular.startItProject.repo.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ImageService {
    @Autowired
    private ImageRepository imageRepository;

    public void saveImage(Project project, String url) {
        imageRepository.save(new Image(project, url));
    }

    public List<String> getUrlsOfProject(Project project) {
        return imageRepository.findByProject(project).stream()
                .map(Image::getUrl)
                .collect(Collectors.toList());
    }
}
