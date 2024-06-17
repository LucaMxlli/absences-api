package com.htlkaindorf.absences_api.repositories;

import com.htlkaindorf.absences_api.entities.Absences;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AbsencesRepository extends CrudRepository<Absences, Long> {
    void deleteByUserId(Long userId);
}
