package org.highload.mappers;

import org.highload.dto.AccountInfoDTO;
import org.highload.dto.AccountShortInfoDTO;
import org.highload.model.stock.Account;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface AccountMapper {

    AccountInfoDTO mapToDTO(Account account);

    AccountShortInfoDTO mapToShortDTO(Account account);

    List<AccountShortInfoDTO> mapListToShortDTO(List<Account> accounts);
}
