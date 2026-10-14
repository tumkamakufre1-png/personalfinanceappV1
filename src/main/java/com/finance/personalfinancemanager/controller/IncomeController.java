package com.finance.personalfinancemanager.controller;

import com.finance.personalfinancemanager.model.Income;
import com.finance.personalfinancemanager.model.User;
import com.finance.personalfinancemanager.repository.IncomeRepository;
import com.finance.personalfinancemanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/income")
@CrossOrigin(origins = "http://localhost:3000")
public class IncomeController {

    @Autowired
    private IncomeRepository incomeRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<Income> getAll() {
        return incomeRepository.findAll();
    }

    @GetMapping("/user/{userId}")
    public List<Income> getByUser(@PathVariable Long userId) {
        return incomeRepository.findByUserId(userId);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Income income) {
        if (income.getUser() == null || income.getUser().getId() == null) {
            return ResponseEntity.badRequest().body("User information is missing");
        }
        User user = userRepository.findById(income.getUser().getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Ensure date exists (optional default: today)
        if (income.getDate() == null) {
            income.setDate(LocalDate.now());
        }

        income.setUser(user);
        return ResponseEntity.ok(incomeRepository.save(income));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Income updated) {
        return ResponseEntity.ok(
                incomeRepository.findById(id).map(inc -> {
                    inc.setSource(updated.getSource());
                    inc.setAmount(updated.getAmount());
                    inc.setDate(updated.getDate());
                    // Keep existing user binding
                    return incomeRepository.save(inc);
                }).orElseThrow(() -> new RuntimeException("Income not found"))
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        if (!incomeRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        incomeRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
