package com.course.startItProject.service.impl;

import com.course.startItProject.entity.Image;
import com.course.startItProject.entity.Project;
import com.course.startItProject.repo.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ImageServiceImpl {
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
