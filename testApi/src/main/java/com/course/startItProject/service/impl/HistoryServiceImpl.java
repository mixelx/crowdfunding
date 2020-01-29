package com.course.startItProject.service.impl;

import com.course.startItProject.entity.Bonus;
import com.course.startItProject.entity.History;
import com.course.startItProject.entity.User;
import com.course.startItProject.repo.HistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistoryServiceImpl {
    @Autowired
    private HistoryRepository historyRepository;

    public List<History> findByUser(User user) {
        return historyRepository.findByUser(user);
    }

    public void save(History history, Bonus bonus, User user) {
        history.setBonus(bonus);
        history.setUser(user);
        historyRepository.save(history);
    }
}
