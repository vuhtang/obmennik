package org.highload.mappers;

import org.highload.dto.WalletDTO;
import org.highload.model.stock.Wallet;
import org.mapstruct.Mapper;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface AccountWalletsMapper {

    WalletDTO map(Wallet value);
    Set<WalletDTO> mapListToDTO(Set<Wallet> walletsList);
}
