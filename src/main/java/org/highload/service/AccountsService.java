package org.highload.service;

import lombok.RequiredArgsConstructor;
import org.highload.model.dto.ShortUserInfoDTO;
import org.highload.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountsService {

    private final AccountRepository accountRepository;

    public List<ShortUserInfoDTO> getAllAccounts() {
//        return accountsRepository.findAll();
        return null;
    }
}
