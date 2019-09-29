package com.course.startItProject.service;

import com.course.startItProject.entity.Bonus;
import com.course.startItProject.entity.Project;
import com.course.startItProject.repo.BonusRepository;
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
