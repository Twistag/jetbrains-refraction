package com.refraction.plugin.intellijrefraction.intentions;

import com.intellij.openapi.actionSystem.AnAction;
import com.refraction.plugin.intellijrefraction.actions.RefactorAction;

public class RefactorIntentionAction extends MyAbstractIntentionAction {

    private static final AnAction action = new RefactorAction();

    @Override
    AnAction getAction() {
        return action;
    }
}
