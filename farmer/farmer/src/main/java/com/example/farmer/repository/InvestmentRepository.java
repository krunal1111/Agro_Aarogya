package com.example.farmer.repository;

import com.example.farmer.model.Investment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InvestmentRepository extends JpaRepository<Investment, Long> {
    List<Investment> findByUserId(Long userId);

    Optional<Investment> findByIdAndUserId(Long id, Long userId);
}
