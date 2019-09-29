package com.angular.startItProject.service;

import com.angular.startItProject.entity.News;
import com.angular.startItProject.entity.Project;
import com.angular.startItProject.repo.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsService {
    @Autowired
    private NewsRepository newsRepository;

    public List<News> findByProject(Project project){
        return newsRepository.findByProject(project);
    }

    public List<News> get5latestNews(){
        return newsRepository.findFirst5ByOrderByIdDesc();
    }

    public void save(News news){
        newsRepository.save(news);
    }

    public void save(News news, String imageUrl, Project project){
        news.setImageUrl(imageUrl);
        news.setProject(project);
        newsRepository.save(news);
    }

}
