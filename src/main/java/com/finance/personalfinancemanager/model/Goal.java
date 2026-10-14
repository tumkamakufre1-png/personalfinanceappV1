package com.finance.personalfinancemanager.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Goal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name; // 🎯 Goal name (e.g. Buy Laptop)

    private Double targetAmount;  // 🎯 Goal target (e.g. 500000)

    private Double savedAmount = 0.0; // 💰 Amount saved so far

    private LocalDate deadline; // 📅 Deadline optional

    private String status = "ACTIVE"; // ACTIVE or COMPLETED

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Goal() {}

    // ✅ Getters & Setters
    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public Double getTargetAmount() { return targetAmount; }

    public void setTargetAmount(Double targetAmount) { this.targetAmount = targetAmount; }

    public Double getSavedAmount() { return savedAmount; }

    public void setSavedAmount(Double savedAmount) { this.savedAmount = savedAmount; }

    public LocalDate getDeadline() { return deadline; }

    public void setDeadline(LocalDate deadline) { this.deadline = deadline; }

    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }

    public User getUser() { return user; }

    public void setUser(User user) { this.user = user; }
}