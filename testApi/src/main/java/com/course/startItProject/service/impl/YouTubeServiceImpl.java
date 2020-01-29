package com.course.startItProject.service.impl;

import org.springframework.stereotype.Service;

@Service
public class YouTubeServiceImpl {
    public String adaptLink(String url) {
        return url.replace("watch?v=", "embed/");
    }
}
