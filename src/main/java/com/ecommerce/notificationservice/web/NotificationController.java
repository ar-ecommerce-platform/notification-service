package com.ecommerce.notificationservice.web;

import com.ecommerce.notificationservice.domain.Notification;
import com.ecommerce.notificationservice.service.NotificationService;
import com.ecommerce.notificationservice.web.dto.NotificationRequest;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/** Endpoints to record and read notifications. */
@RestController
@RequestMapping("/notifications")
public class NotificationController {

  private final NotificationService service;

  public NotificationController(NotificationService service) {
    this.service = service;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Notification record(@Valid @RequestBody NotificationRequest request) {
    return service.record(request.userId(), request.type(), request.message());
  }

  /** The caller's own notifications. {@code X-User-Id} is set by the gateway from the token. */
  @GetMapping
  public List<Notification> mine(@RequestHeader("X-User-Id") String userId) {
    return service.forUser(userId);
  }
}
