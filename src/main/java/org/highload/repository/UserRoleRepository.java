package org.highload.repository;

import org.highload.model.roles.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

    @Query(value = "select * from user_role\n" +
            "    where id in (\n" +
            "        select role_id from customer_to_role where customer_id = 1\n" +
            "        )", nativeQuery = true)
    List<UserRole> findAllByUserId(Long id);
}
