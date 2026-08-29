package com.ecommerce.notificationservice.web.dto;

import jakarta.validation.constraints.NotBlank;

/** Request body to record a notification. */
public record NotificationRequest(
    @NotBlank String userId, @NotBlank String type, @NotBlank String message) {}
