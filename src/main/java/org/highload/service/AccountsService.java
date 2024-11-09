package org.highload.service;

import lombok.RequiredArgsConstructor;
import org.highload.model.dto.ShortUserInfoDTO;
import org.highload.repository.AccountsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountsService {

    private final AccountsRepository accountsRepository;

    public List<ShortUserInfoDTO> getAllAccounts() {
//        return accountsRepository.findAll();
        return null;
    }
}
