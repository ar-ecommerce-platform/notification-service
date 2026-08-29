package com.ecommerce.notificationservice.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class NotificationServiceTest {

  @Test
  void forUser_returnsOnlyThatUsersNotifications() {
    NotificationService service = new NotificationService();
    service.record("ada", "ORDER_CONFIRMED", "Order 1 confirmed");
    service.record("grace", "ORDER_CONFIRMED", "Order 2 confirmed");

    assertThat(service.forUser("ada")).hasSize(1);
    assertThat(service.forUser("ada").get(0).message()).contains("Order 1");
  }
}
