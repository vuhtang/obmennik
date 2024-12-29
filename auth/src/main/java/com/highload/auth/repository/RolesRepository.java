package com.highload.auth.repository;

import com.highload.auth.model.entity.roles.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolesRepository extends JpaRepository<UserRole, Long> {

    Optional<UserRole> findUserRoleByName(String name);
}
