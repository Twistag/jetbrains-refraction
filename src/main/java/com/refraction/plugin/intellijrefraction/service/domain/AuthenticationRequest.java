package com.refraction.plugin.intellijrefraction.service.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthenticationRequest {

    private String userId;
    private String teamId;
}
