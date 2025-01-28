package com.highload.auth.repository;

import com.highload.auth.model.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByEmail(String email);

    @Query(value = "select user_role.name FROM customer_to_role\n" +
            "    join user_role on user_role.id = customer_to_role.role_id\n" +
            "    join customer on customer.id = customer_to_role.customer_id\n" +
            "    where customer_id = :userId", nativeQuery = true)
    List<String> findUserRolesNames(@Param("userId")Long userId);
}
