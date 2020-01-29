package com.course.startItProject.service.impl;

import com.course.startItProject.entity.Bonus;
import com.course.startItProject.entity.Project;
import com.course.startItProject.repo.BonusRepository;
import com.course.startItProject.service.BonusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BonusServiceImpl implements BonusService {

    @Autowired
    private BonusRepository bonusRepository;

    @Override
    public Bonus findById(long bonusId) {
        return bonusRepository.findById(bonusId);
    }

    @Override
    public List<Bonus> findByProject(Project project) {
        return bonusRepository.findByProject(project);
    }

    @Override
    public void save(Bonus bonus) {
        bonusRepository.save(bonus);
    }
}
