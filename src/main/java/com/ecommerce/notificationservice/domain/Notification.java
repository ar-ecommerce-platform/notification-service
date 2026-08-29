package com.ecommerce.notificationservice.domain;

import java.time.Instant;
import java.util.concurrent.atomic.AtomicLong;

/** An immutable notification record held in memory. */
public record Notification(long id, String userId, String type, String message, Instant createdAt) {

  private static final AtomicLong SEQUENCE = new AtomicLong();

  public static Notification create(String userId, String type, String message) {
    return new Notification(SEQUENCE.incrementAndGet(), userId, type, message, Instant.now());
  }
}
