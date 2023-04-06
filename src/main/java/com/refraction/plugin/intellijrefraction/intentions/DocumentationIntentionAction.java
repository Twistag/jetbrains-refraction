package com.refraction.plugin.intellijrefraction.intentions;

import com.intellij.openapi.actionSystem.AnAction;
import com.refraction.plugin.intellijrefraction.actions.DocumentationAction;

public class DocumentationIntentionAction extends MyAbstractIntentionAction {

    private static final AnAction action = new DocumentationAction();

    @Override
    AnAction getAction() {
        return action;
    }
}
