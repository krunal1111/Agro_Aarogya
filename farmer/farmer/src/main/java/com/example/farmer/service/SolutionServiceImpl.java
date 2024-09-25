package com.example.farmer.service;

import com.example.farmer.model.Solution;
import com.example.farmer.repository.SolutionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SolutionServiceImpl implements SolutionService {

    @Autowired
    private SolutionRepository solutionRepository;

    @Override
    public Solution saveSolution(Solution solution) {
        return solutionRepository.save(solution);
    }

    @Override
    public List<Solution> getAllSolutions() {
        return solutionRepository.findAll(); // Implement the method
    }
}
