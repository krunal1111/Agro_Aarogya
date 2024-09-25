package com.example.farmer.controller;

import com.example.farmer.model.Solution;
import com.example.farmer.service.SolutionService;
import com.example.farmer.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/auth/solution")
@CrossOrigin("http://agro-arogya2.s3-website.ap-south-1.amazonaws.com")
public class SolutionController {

    @Autowired
    private SolutionService solutionService;

    @Autowired
    private FileStorageService fileStorageService;

    @PostMapping
    public ResponseEntity<Solution> submitSolution(
            @RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam("description") String description,
            @RequestParam("userId") String userId,
            @RequestParam(value = "file", required = false) MultipartFile file) {

        Solution solution = new Solution();
        solution.setName(name);
        solution.setEmail(email);
        solution.setDescription(description);
        solution.setUserId(userId);

        if (file != null && !file.isEmpty()) {
            try {
                String filename = fileStorageService.storeFile(file);
                solution.setImageUrl(filename);
            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }

        Solution savedSolution = solutionService.saveSolution(solution);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedSolution);
    }

}
