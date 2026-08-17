package com.personalfinance.api.payment_reminder.controller;

import com.personalfinance.api.payment_reminder.dto.request.CreatePaymentReminderRequest;
import com.personalfinance.api.payment_reminder.dto.request.MarkAsExpenseRequest;
import com.personalfinance.api.payment_reminder.dto.request.UpdatePaymentReminderRequest;
import com.personalfinance.api.payment_reminder.dto.response.PaymentReminderResponse;
import com.personalfinance.api.payment_reminder.service.PaymentReminderService;
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
@RequestMapping("/payment-reminders")
@RequiredArgsConstructor
public class PaymentReminderController {

    private final PaymentReminderService paymentReminderService;

    @GetMapping
    public ResponseEntity<List<PaymentReminderResponse>> getAllPaymentReminders() {
        List<PaymentReminderResponse> response = paymentReminderService.getAllPaymentReminders();

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<MessageResponse> createPaymentReminder(@Valid @RequestBody CreatePaymentReminderRequest request) {
        paymentReminderService.createPaymentReminder(request);
        MessageResponse response = MessageResponse.builder()
                .message("Payment reminder has been created successfully!")
                .build();

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<MessageResponse> updatePaymentReminder(@PathVariable("id") Integer id, @Valid @RequestBody UpdatePaymentReminderRequest request) {
        paymentReminderService.updatePaymentReminder(id, request);
        MessageResponse response = MessageResponse.builder()
                .message("Payment reminder has been updated successfully!")
                .build();

        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/expense")
    public ResponseEntity<MessageResponse> markAsExpense(@PathVariable("id") Integer id, @Valid @RequestBody MarkAsExpenseRequest request) {
        paymentReminderService.markAsExpense(id, request);
        MessageResponse response = MessageResponse.builder()
                .message("Payment reminder has been converted into a transaction!")
                .build();

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deletePaymentReminder(@PathVariable("id") Integer id) {
        paymentReminderService.deletePaymentReminder(id);
        MessageResponse response = MessageResponse.builder()
                .message("Payment reminder has been deleted successfully!")
                .build();

        return ResponseEntity.ok(response);
    }
}
