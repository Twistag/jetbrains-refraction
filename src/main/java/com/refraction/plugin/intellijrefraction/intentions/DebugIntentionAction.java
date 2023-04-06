package com.refraction.plugin.intellijrefraction.intentions;

import com.intellij.openapi.actionSystem.AnAction;
import com.refraction.plugin.intellijrefraction.actions.DebugAction;

public class DebugIntentionAction extends MyAbstractIntentionAction {

    private static final AnAction action = new DebugAction();

    @Override
    AnAction getAction() {
        return action;
    }
}
