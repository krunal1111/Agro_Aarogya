package com.example.farmer.service;

import com.example.farmer.model.Solution;
import java.util.List;

public interface SolutionService {
    Solution saveSolution(Solution solution);
    List<Solution> getAllSolutions(); // Add this method declaration
}
