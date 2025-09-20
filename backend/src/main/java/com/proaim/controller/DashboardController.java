package com.proaim.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {
    
    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Map<String, Object>> getDashboardData() {
        Map<String, Object> dashboardData = new HashMap<>();
        
        // Mock data for demonstration
        dashboardData.put("totalUsers", 1250);
        dashboardData.put("activeUsers", 890);
        dashboardData.put("totalRevenue", 45000.00);
        dashboardData.put("monthlyGrowth", 12.5);
        
        Map<String, Integer> monthlyStats = new HashMap<>();
        monthlyStats.put("January", 120);
        monthlyStats.put("February", 135);
        monthlyStats.put("March", 142);
        monthlyStats.put("April", 158);
        monthlyStats.put("May", 165);
        monthlyStats.put("June", 178);
        
        dashboardData.put("monthlyStats", monthlyStats);
        
        return ResponseEntity.ok(dashboardData);
    }
}
