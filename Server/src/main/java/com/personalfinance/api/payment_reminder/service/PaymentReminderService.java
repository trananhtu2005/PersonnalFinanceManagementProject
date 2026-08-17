package com.personalfinance.api.payment_reminder.service;

import com.personalfinance.api.payment_reminder.dto.request.CreatePaymentReminderRequest;
import com.personalfinance.api.payment_reminder.dto.request.MarkAsExpenseRequest;
import com.personalfinance.api.payment_reminder.dto.request.UpdatePaymentReminderRequest;
import com.personalfinance.api.payment_reminder.dto.response.PaymentReminderResponse;
import java.util.List;

public interface PaymentReminderService {

    List<PaymentReminderResponse> getAllPaymentReminders();

    void createPaymentReminder(CreatePaymentReminderRequest request);

    void updatePaymentReminder(Integer id, UpdatePaymentReminderRequest request);

    void markAsExpense(Integer id, MarkAsExpenseRequest request);

    void deletePaymentReminder(Integer id);
}
