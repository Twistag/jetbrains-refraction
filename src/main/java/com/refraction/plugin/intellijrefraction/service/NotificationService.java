package com.refraction.plugin.intellijrefraction.service;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;

public class NotificationService {
    
    public static void showInfoNotification(final String content) {
        notifyNotification("Refraction", content, NotificationType.INFORMATION, content);
    }

    public static void showWarningNotification(final String content) {
        notifyNotification("Refraction", content, NotificationType.WARNING, content);
    }

    public static void showErrorNotification(final String content) {
        notifyNotification("Refraction", content, NotificationType.ERROR, content);
    }

    private static void notifyNotification(final String title, final String content, final NotificationType warning, final String groupId) {
        final Notification notification = new Notification(groupId, title, content, warning);
        Notifications.Bus.notify(notification);
    }

}