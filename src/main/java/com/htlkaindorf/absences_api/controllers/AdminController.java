package com.htlkaindorf.absences_api.controllers;

import com.htlkaindorf.absences_api.dtos.RegisterUserDto;
import com.htlkaindorf.absences_api.entities.Absences;
import com.htlkaindorf.absences_api.entities.User;
import com.htlkaindorf.absences_api.services.AbsencesService;
import com.htlkaindorf.absences_api.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/admins")
@RestController
public class AdminController {
    private final UserService userService;
    private final AbsencesService absencesService;

    public AdminController(UserService userService, AbsencesService absencesService) {
        this.userService = userService;
        this.absencesService = absencesService;
    }

    @PostMapping
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<User> createAdministrator(@RequestBody RegisterUserDto registerUserDto) {
        User createdAdmin = userService.createAdministrator(registerUserDto);
        return ResponseEntity.ok(createdAdmin);
    }

    @DeleteMapping("/users/{userId}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<?> deleteUser(@PathVariable int userId) {
        userService.deleteUserById(userId);
        return ResponseEntity.ok("User deleted successfully");
    }

    @PutMapping("/users/{userId}/absences")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<?> updateAbsences(@PathVariable int userId, @RequestBody List<Absences> absencesList) {
        absencesService.updateUserAbsences(userId, absencesList);
        return ResponseEntity.ok("User absences updated successfully");
    }
}
