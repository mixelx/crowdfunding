package com.angular.startItProject.service;

import com.angular.startItProject.entity.Bonus;
import com.angular.startItProject.entity.Project;
import com.angular.startItProject.repo.BonusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BonusService {
    @Autowired
    private BonusRepository bonusRepository;

    public Bonus findById(long bonusId) {
        return bonusRepository.findById(bonusId);
    }

    public List<Bonus> findByProject(Project project) {
        return bonusRepository.findByProject(project);
    }

    public void save(Bonus bonus) {
        bonusRepository.save(bonus);
    }
}
