package org.highload.service;

import lombok.RequiredArgsConstructor;
import org.highload.model.User;
import org.highload.model.roles.UserRole;
import org.highload.repository.UserRepository;
import org.highload.repository.UserRoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;

    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow();
    }

    public List<UserRole> getUserRolesById(Long id) {
        return userRoleRepository.findAllByUserId(id);
    }
}
