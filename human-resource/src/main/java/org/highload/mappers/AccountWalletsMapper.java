package org.highload.mappers;

import org.highload.dto.WalletDTO;
import org.highload.model.Wallet;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AccountWalletsMapper {

    WalletDTO map(Wallet value);
    List<WalletDTO> mapListToDTO(List<Wallet> walletsList);
}
