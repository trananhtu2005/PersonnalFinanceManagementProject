package com.personalfinance.api.wallet.controller;

import com.personalfinance.api.wallet.dto.request.CreateWalletRequest;
import com.personalfinance.api.wallet.dto.request.UpdateWalletRequest;
import com.personalfinance.api.wallet.dto.response.WalletResponse;
import com.personalfinance.api.wallet.service.WalletService;
import com.personalfinance.common.MessageResponse;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wallets")
@RequiredArgsConstructor
public class WalletController {

    private final WalletService walletService;

    @GetMapping
    public ResponseEntity<List<WalletResponse>> getAllWallets() {
        List<WalletResponse> response = walletService.getAllWallets();

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<MessageResponse> createWallet(@Valid @RequestBody CreateWalletRequest request) {
        walletService.createWallet(request);
        MessageResponse response = MessageResponse.builder()
                .message("Wallet has been created successfully!")
                .build();

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<MessageResponse> updateWallet(@PathVariable("id") Integer id, @RequestBody UpdateWalletRequest request) {
        walletService.updateWallet(id, request);
        MessageResponse response = MessageResponse.builder()
                .message("Wallet has been updated successfully!")
                .build();

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteWallet(@PathVariable("id") Integer id) {
        walletService.deleteWallet(id);
        MessageResponse response = MessageResponse.builder()
                .message("Wallet has been deleted successfully!")
                .build();

        return ResponseEntity.ok(response);
    }
}
