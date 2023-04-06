package com.refraction.plugin.intellijrefraction.service;

import com.intellij.lang.Language;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiFile;
import com.refraction.plugin.intellijrefraction.service.domain.CodeAndLanguage;

public class IntellijServices {

    public String getSelectedText(final AnActionEvent e) {
        final Project project = e.getProject();
        if (project == null) {
            return null;
        }

        final Editor editor = FileEditorManager.getInstance(project).getSelectedTextEditor();
        if (editor == null) {
            return null;
        }

        return editor.getSelectionModel().getSelectedText();
    }

    public String getLanguage(final AnActionEvent e) {
        final Project project = e.getProject();
        if (project == null) {
            return null;
        }

        final Editor editor = FileEditorManager.getInstance(project).getSelectedTextEditor();
        if (editor == null) {
            return null;
        }

        final PsiFile psiFile = PsiDocumentManager.getInstance(project).getPsiFile(editor.getDocument());
        if (psiFile == null) {
            return null;
        }

        final Language language = psiFile.getLanguage();
        return language.getID();
    }

    public CodeAndLanguage getCodeAndLanguage(final AnActionEvent e) {
        return CodeAndLanguage.builder()
                .code(getSelectedText(e))
                .language(getLanguage(e))
                .build();
    }

    public void insertTextAtTheEndOfTheSelection(final AnActionEvent event, final String text) {
        final Editor editor = event.getRequiredData(CommonDataKeys.EDITOR);
        final Document document = editor.getDocument();

        final SelectionModel selectionModel = editor.getSelectionModel();
        int end = selectionModel.getSelectionEnd();
        document.insertString(end, text);
    }
}
