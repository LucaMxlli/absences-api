package com.htlkaindorf.absences_api.services;

import com.htlkaindorf.absences_api.entities.Absences;
import com.htlkaindorf.absences_api.repositories.AbsencesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AbsencesService {
    private final AbsencesRepository absencesRepository;

    @Autowired
    public AbsencesService(AbsencesRepository absencesRepository) {
        this.absencesRepository = absencesRepository;
    }

    public void updateUserAbsences(int userId, List<Absences> absencesList) {
        absencesRepository.deleteByUserId((long) userId); // Remove existing absences for the user
        for (Absences absence : absencesList) {
            absence.setUserId(userId); // Ensure the absences are linked to the correct user
            absencesRepository.save(absence);
        }
    }
}
