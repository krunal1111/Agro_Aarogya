package com.example.farmer.controller;

import com.example.farmer.model.Investment;
import com.example.farmer.repository.InvestmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin("http://agro-arogya2.s3-website.ap-south-1.amazonaws.com")
public class InvestmentController {

    private final InvestmentRepository investmentRepository;

    @Autowired
    public InvestmentController(InvestmentRepository investmentRepository) {
        this.investmentRepository = investmentRepository;
    }

    // Get all investments for a specific user
    @GetMapping("/investment/{userId}")
    public ResponseEntity<List<Investment>> getInvestmentsByUserId(@PathVariable("userId") Long userId) {
        List<Investment> investments = investmentRepository.findByUserId(userId);
        return ResponseEntity.ok(investments.isEmpty() ? List.of() : investments);
    }

    // Create a new investment for a specific user
    @PostMapping("/investment/{userId}")
    public ResponseEntity<Investment> createInvestment(@PathVariable("userId") Long userId, @RequestBody Investment investment) {
        if (investment == null) {
            return ResponseEntity.badRequest().build();
        }
        investment.setUserId(userId);
        investment.setDate(LocalDateTime.now()); // Set the current date and time
        Investment savedInvestment = investmentRepository.save(investment);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedInvestment);
    }

    // Delete an investment by ID for a specific user
    @DeleteMapping("/investment/{id}/{userId}")
    public ResponseEntity<Void> deleteInvestment(@PathVariable("id") Long id, @PathVariable("userId") Long userId) {
        Optional<Investment> investmentOptional = investmentRepository.findByIdAndUserId(id, userId);
        if (investmentOptional.isPresent()) {
            investmentRepository.delete(investmentOptional.get());
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
