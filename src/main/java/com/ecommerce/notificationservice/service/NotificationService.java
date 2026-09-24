package com.ecommerce.notificationservice.service;

import com.ecommerce.notificationservice.domain.Notification;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * In-memory notification store. A real implementation would consume domain events from a broker and
 * dispatch email / SMS; here it simply records and logs them so the order flow is observable.
 */
@Service
public class NotificationService {

  private static final Logger log = LoggerFactory.getLogger(NotificationService.class);

  private final List<Notification> notifications = new CopyOnWriteArrayList<>();

  public Notification record(String userId, String type, String message) {
    Notification notification = Notification.create(userId, type, message);
    notifications.add(notification);
    log.info("notification [{}] user={} : {}", type, userId, message);
    return notification;
  }

  public List<Notification> forUser(String userId) {
    return notifications.stream().filter(n -> n.userId().equals(userId)).toList();
  }
}
