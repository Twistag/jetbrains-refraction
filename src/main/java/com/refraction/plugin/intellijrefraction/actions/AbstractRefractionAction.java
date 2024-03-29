package com.refraction.plugin.intellijrefraction.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.command.WriteCommandAction;
import com.refraction.plugin.intellijrefraction.exceptions.RefractionException;
import com.refraction.plugin.intellijrefraction.service.IntellijServices;
import com.refraction.plugin.intellijrefraction.service.NotificationService;
import com.refraction.plugin.intellijrefraction.service.RefractionBackendService;
import com.refraction.plugin.intellijrefraction.service.domain.CodeAndLanguage;
import org.apache.http.HttpStatus;
import org.jetbrains.annotations.NotNull;

abstract class AbstractRefractionAction extends AnAction {

    private static final RefractionBackendService refractionBackendService = new RefractionBackendService();

    private static final IntellijServices intellijServices = new IntellijServices();

    private static final String API_URL = "/api/generate/";

    abstract String getUtility();

    public void actionPerformed(@NotNull final AnActionEvent event) {
        final CodeAndLanguage codeAndLanguage = getCodeAndLanguage(event);
        runCodeGenerationAction(event, codeAndLanguage);
    }

    void runCodeGenerationAction(@NotNull final AnActionEvent event, final CodeAndLanguage codeAndLanguage) {
        try {
            refractionBackendService.authenticateUserAsync()
                    .thenAcceptAsync(httpResponse -> {
                        if (is2xx(httpResponse.statusCode())) {
                            callApiToGenerateCode(event, codeAndLanguage);
                        } else if (HttpStatus.SC_NOT_FOUND == httpResponse.statusCode()){
                            NotificationService.showWarningNotification("User is not authenticated");
                        } else {
                            NotificationService.showWarningNotification("Could not authenticate: " + httpResponse.body());
                        }
                    });
        } catch (final RefractionException ex) {
            NotificationService.showWarningNotification(ex.getMessage());
        } catch (final Exception ignore) {
        }
    }

    public CodeAndLanguage getCodeAndLanguage(@NotNull final AnActionEvent event) {
        return intellijServices.getCodeAndLanguage(event);
    }

    private void callApiToGenerateCode(@NotNull final AnActionEvent event, final CodeAndLanguage codeAndLanguage) {
        try {
            refractionBackendService.callServiceWithStreamResponseType(
                    API_URL + getUtility(),
                    codeAndLanguage,
                    (text, offset) -> {
                        WriteCommandAction.runWriteCommandAction(event.getProject(), () -> getInsertTextAtTheEndOfTheSelection(event, text, offset));
                        return true;
                    }
            );
        } catch (final RefractionException ex) {
            NotificationService.showWarningNotification(ex.getMessage());
        } catch (final Exception ignore) {
        }
    }

    private void getInsertTextAtTheEndOfTheSelection(@NotNull final AnActionEvent event, final String text, final int offset) {
        intellijServices.insertTextAtTheEndOfTheSelection(event, text, offset);
    }


    private static boolean is2xx(final int statusCode) {
        return statusCode >= 200 && statusCode < 300;
    }
}
