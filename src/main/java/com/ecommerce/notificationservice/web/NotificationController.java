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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

  @GetMapping
  public List<Notification> list(@RequestParam(required = false) String userId) {
    return userId == null ? service.all() : service.forUser(userId);
  }
}
