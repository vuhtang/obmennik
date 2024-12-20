package org.highload.mappers;

import org.highload.dto.WalletDTO;
import org.highload.model.stock.Wallet;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface AccountWalletsMapper {

    WalletDTO map(Wallet value);
    List<WalletDTO> mapListToDTO(List<Wallet> walletsList);
}
