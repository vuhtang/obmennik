package org.highload.repository;

import org.highload.model.roles.ControlAccess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccessesRepository extends JpaRepository<ControlAccess, Long> {

    @Query(value = "select * from control_access\n" +
            "    where id in (\n" +
            "        select access_id from role_to_access where role_id = ?1\n" +
            "        )", nativeQuery = true)
    List<ControlAccess> findAllByUserRoleId(Long id);
}
