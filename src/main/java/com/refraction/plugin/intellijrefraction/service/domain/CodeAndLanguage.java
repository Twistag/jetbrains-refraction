package com.refraction.plugin.intellijrefraction.service.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CodeAndLanguage {

    private String code;
    private String language;
    private String framework;
}
