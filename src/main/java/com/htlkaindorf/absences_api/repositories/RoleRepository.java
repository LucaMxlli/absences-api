package com.htlkaindorf.absences_api.repositories;

import com.htlkaindorf.absences_api.entities.Role;
import com.htlkaindorf.absences_api.enums.RoleEnum;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends CrudRepository<Role, Integer> {
    Optional<Role> findByName(RoleEnum name);
}
