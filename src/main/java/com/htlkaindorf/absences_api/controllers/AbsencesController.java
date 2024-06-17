package com.htlkaindorf.absences_api.controllers;

import com.htlkaindorf.absences_api.entities.Absences;
import com.htlkaindorf.absences_api.entities.User;
import com.htlkaindorf.absences_api.repositories.UserRepository;
import com.htlkaindorf.absences_api.services.AbsencesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/absences")
public class AbsencesController {

    @Autowired
    private AbsencesService absencesService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public ResponseEntity<?> recordAbsence(@AuthenticationPrincipal UserDetails userDetails, @RequestBody Absences absences) {
        User user = userRepository.findByEmail(userDetails.getUsername()).orElseThrow(() -> new IllegalArgumentException("User not found"));
        absencesService.recordAbsence(user.getId(), absences.getDate(), absences.getHoursAbsent());
        return ResponseEntity.ok("Absence recorded successfully");
    }

    @GetMapping("/summary")
    public ResponseEntity<List<Map<String, Object>>> getAbsencesSummary(@RequestParam(required = false, defaultValue = "asc") String sort) {
        List<User> users = (List<User>) userRepository.findAll();
        List<Map<String, Object>> summary = users.stream()
                .map(user -> {
                    Map<String, Object> userMap = new HashMap<>();
                    userMap.put("id", user.getId());
                    userMap.put("fullName", user.getFullName());
                    userMap.put("totalAbsenceHours", absencesService.getTotalAbsenceHours(user.getId()));
                    return userMap;
                })
                .sorted((u1, u2) -> {
                    int comparison = Integer.compare((int) u1.get("totalAbsenceHours"), (int) u2.get("totalAbsenceHours"));
                    return "desc".equalsIgnoreCase(sort) ? -comparison : comparison;
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(summary);
    }
}
