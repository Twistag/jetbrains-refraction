package com.refraction.plugin.intellijrefraction.intentions;

import com.intellij.codeInsight.intention.IntentionAction;
import com.intellij.codeInspection.util.IntentionName;
import com.intellij.ide.DataManager;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFile;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.NotNull;

abstract class MyAbstractIntentionAction implements IntentionAction {

    private final String actionText = ActionManager.getInstance()
            .getAction(getAction().getClass().getName())
            .getTemplatePresentation()
            .getText();

    @Override
    public @IntentionName @NotNull String getText() {
        return "Refraction: " + actionText;
    }

    @NotNull
    @Override
    public String getFamilyName() {
        return getText();
    }

    @Override
    public boolean isAvailable(@NotNull Project project, Editor editor, PsiFile file) {
        return true;
    }

    @Override
    public void invoke(@NotNull Project project, Editor editor, PsiFile file) throws IncorrectOperationException {
        final DataContext dataContext = DataManager.getInstance().getDataContext(editor.getComponent());
        final AnActionEvent event = AnActionEvent.createFromDataContext(ActionPlaces.UNKNOWN, null, dataContext);
        getAction().actionPerformed(event);

    }

    @Override
    public boolean startInWriteAction() {
        return true;
    }

    abstract AnAction getAction();

}
