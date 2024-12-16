package org.highload.mappers;

import org.highload.dto.AccountInfoDTO;
import org.highload.dto.AccountShortInfoDTO;
import org.highload.model.roles.UserRole;
import org.highload.model.Account;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring",injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface AccountMapper {

    static AccountInfoDTO mapToDTO(Account account, List<UserRole> roles) {
        return AccountInfoDTO.builder()
                .id(account.getId())
                .name(account.getUser().getName())
                .surname(account.getUser().getSurname())
                .email(account.getUser().getEmail())
                .dob(account.getUser().getDateOfBirth().toString())
                .roles(
                        roles.stream().map(UserRole::getName).toList()
                )
                .build();
    }

    static AccountShortInfoDTO mapToShortDTO(Account account) {
        return AccountShortInfoDTO.builder()
                .id(account.getId())
                .name(account.getUser().getName())
                .surname(account.getUser().getSurname())
                .email(account.getUser().getEmail())
                .build();
    }

//    AccountInfoDTO mapToDTO(Account account);
//
//    AccountShortInfoDTO mapToShortDTO(Account account);

    List<AccountShortInfoDTO> mapListToShortDTO(List<Account> accounts);
}
