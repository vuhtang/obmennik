package org.highload.mappers;

import org.highload.dto.WalletDTO;
import org.highload.model.stock.Wallet;
import org.mapstruct.Mapper;

import java.util.Set;

@Mapper
public interface AccountWalletsMapper {

    Set<WalletDTO> mapListToDTO(Set<Wallet> walletsList);
}
