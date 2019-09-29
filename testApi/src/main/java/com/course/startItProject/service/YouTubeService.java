package com.course.startItProject.service;

import org.springframework.stereotype.Service;

@Service
public class YouTubeService {
    public String adaptLink(String url) {
        return url.replace("watch?v=", "embed/");
    }
}
