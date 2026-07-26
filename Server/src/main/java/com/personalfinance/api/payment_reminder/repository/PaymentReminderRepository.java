package com.personalfinance.api.payment_reminder.repository;

import com.personalfinance.api.payment_reminder.entity.PaymentReminder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentReminderRepository extends JpaRepository<PaymentReminder, Integer> {

}
