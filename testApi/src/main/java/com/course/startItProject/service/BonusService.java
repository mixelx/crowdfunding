package com.course.startItProject.service;

import com.course.startItProject.entity.Bonus;
import com.course.startItProject.entity.Project;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BonusService {

    Bonus findById(long bonusId);

    List<Bonus> findByProject(Project project);

    void save(Bonus bonus);
}
