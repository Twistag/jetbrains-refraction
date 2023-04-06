package com.refraction.plugin.intellijrefraction.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.options.ShowSettingsUtil;
import com.refraction.plugin.intellijrefraction.actions.wondow.RefractionSettingsWindow;

public class RefractionSettingsAction extends AnAction {

    @Override
    public void actionPerformed(final AnActionEvent e) {
        final RefractionSettingsWindow settings = new RefractionSettingsWindow();
        ShowSettingsUtil.getInstance().editConfigurable(e.getProject(), settings);
    }
}