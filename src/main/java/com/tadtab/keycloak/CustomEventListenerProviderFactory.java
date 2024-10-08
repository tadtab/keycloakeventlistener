package com.tadtab.keycloak;

import lombok.extern.slf4j.Slf4j;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.EventListenerProviderFactory;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.Config;

@Slf4j
public class CustomEventListenerProviderFactory implements EventListenerProviderFactory {


    @Override
    public EventListenerProvider create(KeycloakSession session) {
        return new CustomEventListenerProvider();
    }

    @Override
    public void init(Config.Scope config) {
        log.info("Initializing Custom Event Listener Provider Factory");
    }

    @Override
    public void postInit(KeycloakSessionFactory factory) {
        // Optional post-initialization steps
    }

    @Override
    public void close() {
        log.info("Closing Custom Event Listener Provider Factory");
    }

    @Override
    public String getId() {
        return "custom-event-listener";
    }
}
