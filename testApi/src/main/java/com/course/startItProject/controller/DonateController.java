package com.course.startItProject.controller;

import com.course.startItProject.entity.*;
import com.course.startItProject.service.impl.BonusServiceImpl;
import com.course.startItProject.service.impl.DonateServiceImpl;
import com.course.startItProject.service.impl.HistoryServiceImpl;
import com.course.startItProject.service.impl.ProjectServiceImpl;
import com.course.startItProject.service.impl.SessionServiceImpl;
import com.course.startItProject.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DonateController {
    @Autowired
    private BonusServiceImpl bonusServiceImpl;

    @Autowired
    private ProjectServiceImpl projectServiceImpl;

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Autowired
    DonateServiceImpl donateServiceImpl;

    @Autowired
    SessionServiceImpl sessionServiceImpl;

    @Autowired
    HistoryServiceImpl historyServiceImpl;

    @PostMapping("/donate")
    public String donate(@RequestParam("id") @ModelAttribute long projectId, @ModelAttribute Donate donate,
                         @AuthenticationPrincipal User user, ModelMap modelMap) {
        Project project = projectServiceImpl.findProjectById(projectId);
        donate.setUserId(user);
        donate.setProjectId(project);
        donateServiceImpl.save(donate);

        project.setReached(project.getReached() + (double) donate.getSum());
        projectServiceImpl.save(project);
        modelMap.addAttribute("Success", "You've donated successfully!");
        return "redirect:/project?id=" + projectId;
    }

    @PostMapping("/buy")
    public String buyBonus(@RequestParam("bonusId") long bonusId, @ModelAttribute("history") History history) {
        User user = sessionServiceImpl.getCurrentUser(userServiceImpl);
        Bonus bonus = bonusServiceImpl.findById(bonusId);
        Project project = bonus.getProject();
        project.setReached(project.getReached() + bonus.getCost());
        projectServiceImpl.save(project);
        historyServiceImpl.save(history, bonus, user);
        return "redirect:/project?id=" + bonus.getProject().getId();
    }
}
