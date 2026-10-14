package com.finance.personalfinancemanager.controller;

import com.finance.personalfinancemanager.model.Goal;
import com.finance.personalfinancemanager.model.User;
import com.finance.personalfinancemanager.repository.GoalRepository;
import com.finance.personalfinancemanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/goals")
@CrossOrigin(origins = "http://localhost:3000")
public class GoalController {

    @Autowired
    private GoalRepository goalRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public Goal createGoal(@RequestBody Goal goal) {
        if (goal.getUser() == null || goal.getUser().getId() == null) {
            throw new RuntimeException("User ID missing");
        }

        User user = userRepository.findById(goal.getUser().getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        goal.setUser(user);
        return goalRepository.save(goal);
    }

    @GetMapping("/user/{userId}")
    public List<Goal> getUserGoals(@PathVariable Long userId) {
        return goalRepository.findByUserId(userId);
    }

    // ✅ UPDATE goal (savedAmount OR status OR name etc.)
    @PutMapping("/{id}")
    public Goal updateGoal(@PathVariable Long id, @RequestBody Goal updatedGoal) {
        return goalRepository.findById(id).map(goal -> {

            if (updatedGoal.getSavedAmount() != null) {
                goal.setSavedAmount(updatedGoal.getSavedAmount());
            }

            if (updatedGoal.getStatus() != null) {
                goal.setStatus(updatedGoal.getStatus());
            }

            if (updatedGoal.getName() != null) {
                goal.setName(updatedGoal.getName());
            }

            if (updatedGoal.getDeadline() != null) {
                goal.setDeadline(updatedGoal.getDeadline());
            }

            if (updatedGoal.getTargetAmount() != null) {
                goal.setTargetAmount(updatedGoal.getTargetAmount());
            }

            return goalRepository.save(goal);
        }).orElseThrow(() -> new RuntimeException("Goal not found"));
    }

    @DeleteMapping("/{id}")
    public void deleteGoal(@PathVariable Long id) {
        goalRepository.deleteById(id);
    }
}