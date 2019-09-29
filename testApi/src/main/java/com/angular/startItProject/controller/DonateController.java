package com.angular.startItProject.controller;

import com.angular.startItProject.entity.*;
import com.angular.startItProject.service.*;
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
    private BonusService bonusService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private UserService userService;

    @Autowired
    DonateService donateService;

    @Autowired
    SessionService sessionService;

    @Autowired
    HistoryService historyService;

    @PostMapping("/donate")
    public String donate(@RequestParam("id") @ModelAttribute long projectId, @ModelAttribute Donate donate,
                         @AuthenticationPrincipal User user, ModelMap modelMap) {
        Project project = projectService.findProjectById(projectId);
        donate.setUserId(user);
        donate.setProjectId(project);
        donateService.save(donate);

        project.setReached(project.getReached() + (double) donate.getSum());
        projectService.save(project);
        modelMap.addAttribute("Success", "You've donated successfully!");
        return "redirect:/project?id=" + projectId;
    }

    @PostMapping("/buy")
    public String buyBonus(@RequestParam("bonusId") long bonusId, @ModelAttribute("history") History history) {
        User user = sessionService.getCurrentUser(userService);
        Bonus bonus = bonusService.findById(bonusId);
        Project project = bonus.getProject();
        project.setReached(project.getReached() + bonus.getCost());
        projectService.save(project);
        historyService.save(history, bonus, user);
        return "redirect:/project?id=" + bonus.getProject().getId();
    }
}
