package com.personalfinance.api.wallet.service.impl;

import com.personalfinance.api.user.entity.User;
import com.personalfinance.api.user.service.CurrentUserService;
import com.personalfinance.api.wallet.dto.request.CreateWalletRequest;
import com.personalfinance.api.wallet.dto.request.UpdateWalletRequest;
import com.personalfinance.api.wallet.dto.response.WalletResponse;
import com.personalfinance.api.wallet.entity.Wallet;
import com.personalfinance.api.wallet.repository.WalletRepository;
import com.personalfinance.api.wallet.service.WalletService;
import com.personalfinance.exception.AppException;
import com.personalfinance.exception.ErrorCode;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;
    private final CurrentUserService currentUserService;

    @Override
    public List<WalletResponse> getAllWallets() {
        User user = currentUserService.getCurrentUser();
        List<Wallet> wallets = walletRepository.findByUserAndDeletedFalse(user);

        return wallets.stream().map(w
                -> WalletResponse.builder()
                        .id(w.getId())
                        .name(w.getName())
                        .balance(w.getBalance())
                        .build()
        ).toList();
    }

    @Override
    public void createWallet(CreateWalletRequest request) {
        User user = currentUserService.getCurrentUser();

        if (walletRepository.existsByUserAndNameAndDeletedFalse(user, request.getName())) {
            throw new AppException(ErrorCode.WALLET_ALREADY_EXISTS);
        }

        Wallet wallet = Wallet.builder()
                .name(request.getName())
                .balance(request.getBalance())
                .deleted(false)
                .user(user)
                .build();
        walletRepository.save(wallet);
    }

    @Override
    public void updateWallet(Integer id, UpdateWalletRequest request) {
        if (request.isEmpty()) {
            throw new AppException(ErrorCode.NO_DATA_TO_UPDATE);
        }

        User user = currentUserService.getCurrentUser();
        Wallet wallet = walletRepository.findByIdAndUserAndDeletedFalse(id, user)
                .orElseThrow(() -> new AppException(ErrorCode.WALLET_NOT_FOUND));

        if (request.getName() != null) {
            if (walletRepository.existsByUserAndNameAndDeletedFalse(user, request.getName())) {
                throw new AppException(ErrorCode.WALLET_ALREADY_EXISTS);
            }

            wallet.setName(request.getName());
        }
        if (request.getBalance() != null) {
            wallet.setBalance(request.getBalance());
        }

        walletRepository.save(wallet);
    }

    @Override
    public void deleteWallet(Integer id) {
        User user = currentUserService.getCurrentUser();
        Wallet wallet = walletRepository.findByIdAndUserAndDeletedFalse(id, user)
                .orElseThrow(() -> new AppException(ErrorCode.WALLET_NOT_FOUND));
        walletRepository.delete(wallet);
    }
}
