package com.personalfinance.api.notification.repository;

import com.personalfinance.api.notification.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Integer> {

}
