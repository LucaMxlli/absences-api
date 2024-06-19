package com.htlkaindorf.absences_api.services;

import com.htlkaindorf.absences_api.entities.Absences;
import com.htlkaindorf.absences_api.repositories.AbsencesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AbsencesService {
    private final AbsencesRepository absencesRepository;

    @Autowired
    public AbsencesService(AbsencesRepository absencesRepository) {
        this.absencesRepository = absencesRepository;
    }

    public void updateUserAbsences(int userId, List<Absences> absencesList) {
        absencesRepository.deleteByUserId(userId); // Remove existing absences for the user
        for (Absences absence : absencesList) {
            absence.setUserId(userId); // Ensure the absences are linked to the correct user
            absencesRepository.save(absence);
        }
    }

    //function to record absensce for user, insert
    public void insertAbsenceForUser(int userId, LocalDate date, int hoursAbsent) {
        Absences absence = new Absences();
        absence.setUserId(userId);
        absence.setDate(date);
        absence.setHoursAbsent(hoursAbsent);
        absencesRepository.save(absence);
    }

    //function to get all absences from a user
    public int getAbsenceCountForUser(int userId) {
       List<Absences> absences = absencesRepository.findByUserId(userId);
       //iterate threw absences and return the count of hoursAbsent
        int count = 0;
        for(Absences absence : absences) {
            count += absence.getHoursAbsent();
        }
        return count;
    }

}
