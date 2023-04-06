package com.refraction.plugin.intellijrefraction.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.command.WriteCommandAction;
import com.refraction.plugin.intellijrefraction.exceptions.RefractionException;
import com.refraction.plugin.intellijrefraction.service.IntellijServices;
import com.refraction.plugin.intellijrefraction.service.NotificationService;
import com.refraction.plugin.intellijrefraction.service.RefractionBackendService;
import com.refraction.plugin.intellijrefraction.service.domain.CodeAndLanguage;
import org.jetbrains.annotations.NotNull;

import java.net.http.HttpResponse;

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
                            System.out.println("User is authenticated");
                            callApiToGenerateCode(event, codeAndLanguage);
                        } else {
                            System.out.println("User is not authenticated");
                            NotificationService.showWarningNotification("User is not authenticated");
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
            refractionBackendService.callGenerateServiceAsync(API_URL + getUtility(), codeAndLanguage)
                    .thenAcceptAsync(stringHttpResponse -> WriteCommandAction.runWriteCommandAction(event.getProject(), () -> getInsertTextAtTheEndOfTheSelection(event, stringHttpResponse)));
        } catch (final RefractionException ex) {
            NotificationService.showWarningNotification(ex.getMessage());
        } catch (final Exception ignore) {
        }
    }

    private void getInsertTextAtTheEndOfTheSelection(@NotNull final AnActionEvent event, final HttpResponse<String> stringHttpResponse) {
        intellijServices.insertTextAtTheEndOfTheSelection(event, "\n\n" + stringHttpResponse.body());
    }


    private static boolean is2xx(final int statusCode) {
        return statusCode >= 200 && statusCode < 300;
    }
}