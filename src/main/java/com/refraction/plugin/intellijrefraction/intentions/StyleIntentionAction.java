package com.refraction.plugin.intellijrefraction.intentions;

import com.intellij.openapi.actionSystem.AnAction;
import com.refraction.plugin.intellijrefraction.actions.LiteralsAction;
import com.refraction.plugin.intellijrefraction.actions.StyleAction;

public class StyleIntentionAction extends MyAbstractIntentionAction {

    private static final AnAction action = new StyleAction();

    @Override
    AnAction getAction() {
        return action;
    }
}
