package com.personalfinance.api.wallet.service;

import com.personalfinance.api.wallet.dto.request.CreateWalletRequest;
import com.personalfinance.api.wallet.dto.request.UpdateWalletRequest;
import com.personalfinance.api.wallet.dto.response.WalletResponse;
import com.personalfinance.api.wallet.entity.Wallet;
import java.math.BigDecimal;
import java.util.List;

public interface WalletService {

    List<WalletResponse> getAllWallets();

    void createWallet(CreateWalletRequest request);

    void updateWallet(Integer id, UpdateWalletRequest request);

    void deleteWallet(Integer id);

    void addBalance(Wallet wallet, BigDecimal amount);

    void subtractBalance(Wallet wallet, BigDecimal amount);
}
