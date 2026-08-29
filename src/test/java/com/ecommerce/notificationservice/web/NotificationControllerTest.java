package com.ecommerce.notificationservice.web;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.ecommerce.notificationservice.domain.Notification;
import com.ecommerce.notificationservice.service.NotificationService;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(NotificationController.class)
class NotificationControllerTest {

  @Autowired private MockMvc mvc;

  @MockitoBean private NotificationService service;

  @Test
  void record_returns201() throws Exception {
    when(service.record(eq("ada"), eq("ORDER_CONFIRMED"), eq("Order 1 confirmed")))
        .thenReturn(Notification.create("ada", "ORDER_CONFIRMED", "Order 1 confirmed"));

    mvc.perform(
            post("/notifications")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    "{\"userId\":\"ada\",\"type\":\"ORDER_CONFIRMED\",\"message\":\"Order 1 confirmed\"}"))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.type").value("ORDER_CONFIRMED"));
  }

  @Test
  void list_byUser_delegatesToService() throws Exception {
    when(service.forUser("ada"))
        .thenReturn(List.of(Notification.create("ada", "ORDER_CONFIRMED", "hi")));

    mvc.perform(get("/notifications").param("userId", "ada"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].userId").value("ada"));
  }

  @Test
  void record_rejectsBlankFields() throws Exception {
    mvc.perform(
            post("/notifications")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"userId\":\"\",\"type\":\"\",\"message\":\"\"}"))
        .andExpect(status().isBadRequest());
  }
}
