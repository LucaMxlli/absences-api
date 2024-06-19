package com.htlkaindorf.absences_api.repositories;

import com.htlkaindorf.absences_api.entities.Absences;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AbsencesRepository extends CrudRepository<Absences, Long> {
    void deleteByUserId(Integer userId);
    List<Absences> findByUserId(Integer userId);
}
