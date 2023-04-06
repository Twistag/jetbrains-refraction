package com.refraction.plugin.intellijrefraction.intentions;

import com.intellij.openapi.actionSystem.AnAction;
import com.refraction.plugin.intellijrefraction.actions.BugsAction;

public class BugsIntentionAction extends MyAbstractIntentionAction {

    private static final AnAction action = new BugsAction();

    @Override
    AnAction getAction() {
        return action;
    }
}
