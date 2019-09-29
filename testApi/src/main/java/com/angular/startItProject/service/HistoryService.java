package com.angular.startItProject.service;

import com.angular.startItProject.entity.Bonus;
import com.angular.startItProject.entity.History;
import com.angular.startItProject.entity.User;
import com.angular.startItProject.repo.HistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistoryService {
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
