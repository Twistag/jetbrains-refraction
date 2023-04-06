package com.refraction.plugin.intellijrefraction.service.domain;

import lombok.Data;

import java.util.Collections;
import java.util.List;

@Data
public class LanguageProps {

    private String value;
    private String label;
    private List<FrameworkProps> frameworks;

    public List<FrameworkProps> getFrameworks() {
        if (frameworks == null) {
            frameworks = Collections.emptyList();
        }
        return frameworks;
    }
}
