package com.refraction.plugin.intellijrefraction.actions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.ui.NonEmptyInputValidator;
import com.refraction.plugin.intellijrefraction.exceptions.RefractionException;
import com.refraction.plugin.intellijrefraction.service.NotificationService;
import com.refraction.plugin.intellijrefraction.service.RefractionBackendService;
import com.refraction.plugin.intellijrefraction.service.domain.CodeAndLanguage;
import com.refraction.plugin.intellijrefraction.service.domain.FrameworkProps;
import com.refraction.plugin.intellijrefraction.service.domain.LanguageProps;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

public class UnitTestsAction extends AbstractRefractionAction {

    private static List<LanguageProps> languages;
    private static final RefractionBackendService refractionBackendService = new RefractionBackendService();
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    String getUtility() {
        return "unit-tests";
    }

    @Override
    public void actionPerformed(@NotNull final AnActionEvent event) {
        try {
            final CodeAndLanguage codeAndLanguage = getCodeAndLanguage(event);
            if (languages == null) {
                System.out.println("languages are empty");
                languages = getLanguages(event, codeAndLanguage);
            }
            final Optional<LanguageProps> requestedLang = languages.stream()
                    .filter(lang -> lang.getValue().equalsIgnoreCase(codeAndLanguage.getLanguage()))
                    .findAny();

            if (requestedLang.isEmpty()) {
                throw new RefractionException("Language not supported.");
            }

            final String[] frameworks = requestedLang.get()
                    .getFrameworks()
                    .stream()
                    .map(FrameworkProps::getLabel)
                    .toArray(String[]::new);

            final String message = String.format("%s detected. Please select a unit testing framework...", codeAndLanguage.getLanguage());
            SwingUtilities.invokeLater(() -> {
                String chooseTestFramework = Messages.showEditableChooseDialog(message, "Choose Test Framework", null, frameworks, frameworks[0], new NonEmptyInputValidator());
                codeAndLanguage.setFramework(chooseTestFramework);
                runCodeGenerationAction(event, codeAndLanguage);
            });
        } catch (final RefractionException ex) {
            NotificationService.showWarningNotification(ex.getMessage());
        } catch (final Exception ignore) {
        }
    }

    private List<LanguageProps> getLanguages(final AnActionEvent event, final CodeAndLanguage codeAndLanguage) throws JsonProcessingException {
        final String languagesStr = refractionBackendService.callGenerateService("/api/vscode/languages", codeAndLanguage);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return objectMapper.readValue(languagesStr, new TypeReference<>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        });
    }
}
