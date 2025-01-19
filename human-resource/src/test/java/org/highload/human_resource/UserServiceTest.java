package org.highload.human_resource;

import org.highload.model.User;
import org.highload.model.roles.UserRole;
import org.highload.repository.UserRepository;
import org.highload.repository.UserRoleRepository;
import org.highload.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserRoleRepository userRoleRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateTestUser() {
        User user = new User(1L, "Stas", "Stasyamba", "nefor2007@mail.ru", Date.valueOf(LocalDate.now()), Set.of());

        when(userRepository.save(any(User.class))).thenReturn(user);

        User createdUser = userService.createTestUser();

        assertEquals(user, createdUser);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void testGetUserById() {
        Long userId = 1L;
        User user = new User(userId, "Stas", "Stasyamba", "nefor2007@mail.ru", Date.valueOf(LocalDate.now()), Set.of());

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        User foundUser = userService.getUserById(userId);

        assertEquals(user, foundUser);
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    public void testGetUserByIdNotFound() {
        Long userId = 1L;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> userService.getUserById(userId));

        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    public void testGetUserRolesById() {
        Long userId = 1L;
        List<UserRole> userRoles = List.of(new UserRole(), new UserRole());

        when(userRoleRepository.findAllByUserId(userId)).thenReturn(userRoles);

        List<UserRole> foundRoles = userService.getUserRolesById(userId);

        assertEquals(userRoles, foundRoles);
        verify(userRoleRepository, times(1)).findAllByUserId(userId);
    }
}
