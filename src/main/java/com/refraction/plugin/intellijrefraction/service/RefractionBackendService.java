package com.refraction.plugin.intellijrefraction.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.intellij.openapi.util.text.StringUtil;
import com.refraction.plugin.intellijrefraction.exceptions.RefractionException;
import com.refraction.plugin.intellijrefraction.service.domain.AuthenticationRequest;
import com.refraction.plugin.intellijrefraction.service.domain.CodeAndLanguage;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

public class RefractionBackendService {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final PropertyService propertyService = new PropertyService();
    private static final HttpClient client = HttpClient.newHttpClient();
    private static final String baseUrl = "https://www.refraction.dev";

    public CompletableFuture<HttpResponse<String>> authenticateUserAsync() throws IOException {
        System.out.println("Authenticating user async");
        final AuthenticationRequest authenticationRequest = propertyService.getAuthenticationRequest();

        if (StringUtil.isEmpty(authenticationRequest.getUserId())) {
            throw new RefractionException("Please set your Refraction User ID from tools menu");
        }

        final HttpRequest request = getHttpRequest(baseUrl + "/api/vscode/authenticate", authenticationRequest, authenticationRequest);

        return client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
    }

    public CompletableFuture<HttpResponse<String>> callGenerateServiceAsync(final String url, final CodeAndLanguage codeAndLanguage) {
        System.out.println("Calling generate service async with url: " + url);

        try {
            if (StringUtil.isEmpty(codeAndLanguage.getCode())) {
                throw new RefractionException("No code selected");
            }

            if (StringUtil.isEmpty(codeAndLanguage.getLanguage())) {
                throw new RefractionException("No language set");
            }

            if (codeAndLanguage.getCode().length() > 3000) {
                throw new RefractionException(String.format("Sorry, but the code you've selected is too long (%d tokens). Please reduce the length of your code to 3000 tokens or less.", codeAndLanguage.getCode().length()));
            }

            final AuthenticationRequest authenticationRequest = propertyService.getAuthenticationRequest();
            final HttpRequest request = getHttpRequest(baseUrl + url, authenticationRequest, codeAndLanguage);

            return client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
        } catch (final RefractionException ex) {
            NotificationService.showWarningNotification(ex.getMessage());
        } catch (final Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public String callGenerateService(final String url, final CodeAndLanguage codeAndLanguage) {
        System.out.println("Calling generate service with url: " + url);

        try {
            if (StringUtil.isEmpty(codeAndLanguage.getCode())) {
                throw new RefractionException("No code selected");
            }

            if (StringUtil.isEmpty(codeAndLanguage.getLanguage())) {
                throw new RefractionException("No language set");
            }

            if (codeAndLanguage.getCode().length() > 3000) {
                throw new RefractionException(String.format("Sorry, but the code you've selected is too long (%d tokens). Please reduce the length of your code to 3000 tokens or less.", codeAndLanguage.getCode().length()));
            }

            final AuthenticationRequest authenticationRequest = propertyService.getAuthenticationRequest();
            final HttpRequest request = getHttpRequest(baseUrl + url, authenticationRequest, codeAndLanguage);

            final HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (final RefractionException ex) {
            NotificationService.showWarningNotification(ex.getMessage());
        } catch (final Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private static HttpRequest getHttpRequest(final String url, final AuthenticationRequest authenticationRequest, final Object requestBody) throws JsonProcessingException {
        return HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .header("X-Refraction-Source", "jetbrains")
                .header("X-Refraction-User", authenticationRequest.getUserId())
                .header("X-Refraction-Team", authenticationRequest.getTeamId())
                .POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(requestBody)))
                .build();
    }
}