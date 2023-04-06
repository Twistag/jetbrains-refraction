package com.refraction.plugin.intellijrefraction.intentions;

import com.intellij.openapi.actionSystem.AnAction;
import com.refraction.plugin.intellijrefraction.actions.TypesAction;
import com.refraction.plugin.intellijrefraction.actions.UnitTestsAction;

public class UnitTestsIntentionAction extends MyAbstractIntentionAction {

    private static final AnAction action = new UnitTestsAction();

    @Override
    AnAction getAction() {
        return action;
    }
}
