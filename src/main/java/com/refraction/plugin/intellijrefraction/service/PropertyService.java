package com.refraction.plugin.intellijrefraction.service;

import com.intellij.ide.util.PropertiesComponent;
import com.refraction.plugin.intellijrefraction.service.domain.AuthenticationRequest;

public class PropertyService {
    public static final String USER_ID = "refraction.user.id";
    public static final String TEAM_ID = "refraction.team.id";
    private final PropertiesComponent propertiesComponent;

    public PropertyService() {
        propertiesComponent = PropertiesComponent.getInstance();
    }

    public void setMyProperty(final String key, final String value) {
        propertiesComponent.setValue(key, value);
    }

    public String getMyProperty(final String key) {
        return propertiesComponent.getValue(key);
    }

    public AuthenticationRequest getAuthenticationRequest() {
        return AuthenticationRequest.builder()
                .userId(getMyProperty(USER_ID))
                .teamId(getMyProperty(TEAM_ID))
                .build();
    }
}