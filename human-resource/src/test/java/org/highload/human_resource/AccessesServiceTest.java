package org.highload.human_resource;

import org.highload.model.roles.ControlAccess;
import org.highload.repository.AccessesRepository;
import org.highload.service.AccessesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class AccessesServiceTest {

    @Mock
    private AccessesRepository accessesRepository;

    @InjectMocks
    private AccessesService accessesService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllByRoleId() {
        Long roleId = 1L;
        ControlAccess access1 = new ControlAccess();
        access1.setId(1L);
        access1.setName("access1");
        ControlAccess access2 = new ControlAccess();
        access2.setId(2L);
        access2.setName("access2");
        List<ControlAccess> accesses = Arrays.asList(access1, access2);

        when(accessesRepository.findAllByUserRoleId(roleId)).thenReturn(accesses);

        List<ControlAccess> result = accessesService.getAllByRoleId(roleId);

        assertEquals(accesses, result);
        verify(accessesRepository, times(1)).findAllByUserRoleId(roleId);
    }
}
