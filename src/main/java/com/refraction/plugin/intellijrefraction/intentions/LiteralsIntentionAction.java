package com.refraction.plugin.intellijrefraction.intentions;

import com.intellij.openapi.actionSystem.AnAction;
import com.refraction.plugin.intellijrefraction.actions.LiteralsAction;

public class LiteralsIntentionAction extends MyAbstractIntentionAction {

    private static final AnAction action = new LiteralsAction();

    @Override
    AnAction getAction() {
        return action;
    }
}
