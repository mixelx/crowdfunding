package com.course.startItProject.service.impl;

import com.course.startItProject.entity.Donate;
import com.course.startItProject.repo.DonateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DonateServiceImpl {
    @Autowired
    DonateRepository donateRepository;

    public void save(Donate donate){
        donateRepository.save(donate);
    }

    public List<Donate> get5latestDonates(){
        return donateRepository.findFirst5ByOrderByIdDesc();
    }
}
