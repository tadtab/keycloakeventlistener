package com.tadtab.keycloak;


import lombok.extern.slf4j.Slf4j;
import org.keycloak.events.Event;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.admin.AdminEvent;

import org.springframework.web.client.RestTemplate;

@Slf4j
public class CustomEventListenerProvider implements EventListenerProvider {

    @Override
    public void onEvent(Event event) {
        log.info("An event has been emitted {} ", event);
        // Handle login and logout events
        if (event.getType().toString().equals("LOGIN")) {
            log.info("User {} logged in", event.getUserId());
            sendEventToClientApp(event, "LOGIN");
        } else if (event.getType().toString().equals("LOGOUT")) {
            log.info("User {} logged out", event.getUserId());
            sendEventToClientApp(event, "LOGOUT");
        }
    }

    @Override
    public void onEvent(AdminEvent adminEvent, boolean includeRepresentation) {
        log.info("Admin event has been emitted {} {}: ", adminEvent, includeRepresentation);
        sendEventToClientApp(adminEvent, "Any event type");
        // Handle admin events such as user deletion
        if (adminEvent.getOperationType().toString().equals("DELETE")) {
            log.info("User {} deleted", adminEvent.getResourcePath());
            sendEventToClientApp(adminEvent, "DELETE");
        }
    }

    @Override
    public void close() {
        // Handle resource cleanup if needed
    }

    private void sendEventToClientApp(Object event, String eventType) {
        String clientApiUrl = "http://host.docker.internal:8081/api/v1/keycloak-events";  // URL for Spring Boot API
        RestTemplate restTemplate = new RestTemplate();
        System.out.println("Sending event and event type frrom rest client: " +
                event + " " + eventType);
        try {
            restTemplate.postForObject(clientApiUrl, event, String.class);
        } catch (Exception e) {
            log.error("Failed to send event to client app: {}", e);
        }
    }
}


