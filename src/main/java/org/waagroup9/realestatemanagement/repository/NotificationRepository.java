package org.waagroup9.realestatemanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.waagroup9.realestatemanagement.model.entity.Notification;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findNotificationsByUserId(Long userId);
}