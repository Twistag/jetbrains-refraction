package com.refraction.plugin.intellijrefraction.service;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.*;

import javax.swing.*;

public class MyToolWindowFactory implements ToolWindowFactory {

    private final String message;

    public MyToolWindowFactory(final String message) {
        this.message = message;
    }

    @Override
    public void createToolWindowContent(final Project project, final ToolWindow toolWindow) {
        // Create a new JLabel with the message
        final JLabel label = new JLabel(message);

        // Create a new content for the tool window
        final ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
        final Content content = contentFactory.createContent(label, "", false);

        // Add the content to the tool window and show it
        final ContentManager contentManager = toolWindow.getContentManager();
        contentManager.addContent(content);
        toolWindow.activate(null);

        // Set the content manager listener to automatically remove the content when it's hidden
        contentManager.addContentManagerListener(new ContentManagerAdapter() {
            @Override
            public void contentRemoved(final ContentManagerEvent event) {
                if (event.getContent() == content) {
                    toolWindow.getContentManager().removeContentManagerListener(this);
                    toolWindow.getContentManager().removeContent(content, true);
                }
            }
        });
    }

}
