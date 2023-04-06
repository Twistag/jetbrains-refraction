package com.refraction.plugin.intellijrefraction.intentions;

import com.intellij.openapi.actionSystem.AnAction;
import com.refraction.plugin.intellijrefraction.actions.StyleAction;
import com.refraction.plugin.intellijrefraction.actions.TypesAction;

public class TypesIntentionAction extends MyAbstractIntentionAction {

    private static final AnAction action = new TypesAction();

    @Override
    AnAction getAction() {
        return action;
    }
}
