package org.highload.service;

import lombok.RequiredArgsConstructor;
import org.highload.model.roles.ControlAccess;
import org.highload.repository.AccessesRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccessesService {

    private final AccessesRepository accessesRepository;


    public List<ControlAccess> getAllByRoleId(Long id){
        return accessesRepository.findAllByUserRoleId(id);
    }
}
