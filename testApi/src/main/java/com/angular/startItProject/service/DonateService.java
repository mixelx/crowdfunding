package com.angular.startItProject.service;

import com.angular.startItProject.entity.Donate;
import com.angular.startItProject.repo.DonateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DonateService {
    @Autowired
    DonateRepository donateRepository;

    public void save(Donate donate){
        donateRepository.save(donate);
    }

    public List<Donate> get5latestDonates(){
        return donateRepository.findFirst5ByOrderByIdDesc();
    }
}
