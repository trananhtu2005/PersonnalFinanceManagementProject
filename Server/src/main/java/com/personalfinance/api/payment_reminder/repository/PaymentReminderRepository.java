package com.personalfinance.api.payment_reminder.repository;

import com.personalfinance.api.payment_reminder.entity.PaymentReminder;
import com.personalfinance.api.user.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentReminderRepository extends JpaRepository<PaymentReminder, Integer> {

    Optional<PaymentReminder> findByIdAndUser(Integer id, User user);

    @EntityGraph(attributePaths = "category")
    List<PaymentReminder> findByUser(User user);
}
