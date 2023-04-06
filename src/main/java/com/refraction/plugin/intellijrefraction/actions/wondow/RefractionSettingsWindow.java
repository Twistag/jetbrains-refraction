package com.refraction.plugin.intellijrefraction.actions.wondow;

import com.intellij.openapi.options.Configurable;
import com.refraction.plugin.intellijrefraction.service.PropertyService;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class RefractionSettingsWindow implements Configurable {
    private JPanel panel;
    private JTextField userIdTextField;
    private JTextField teamIdTextField;

    private final PropertyService propertyService = new PropertyService();

    @Nls(capitalization = Nls.Capitalization.Title)
    @Override
    public String getDisplayName() {
        return "Refraction Settings";
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        panel = new JPanel();
        userIdTextField = new JTextField(20);
        teamIdTextField = new JTextField(20);
        panel.add(new JLabel("User Id"));
        panel.add(userIdTextField);
        panel.add(new JLabel("Team Id"));
        panel.add(teamIdTextField);
        return panel;
    }

    @Override
    public boolean isModified() {
        return !(
                userIdTextField.getText().equals(propertyService.getMyProperty(PropertyService.USER_ID)) &&
                teamIdTextField.getText().equals(propertyService.getMyProperty(PropertyService.TEAM_ID))
        );
    }

    @Override
    public void apply() {
        propertyService.setMyProperty(PropertyService.USER_ID, userIdTextField.getText());
        propertyService.setMyProperty(PropertyService.TEAM_ID, teamIdTextField.getText());
    }

    @Override
    public void reset() {
        userIdTextField.setText(propertyService.getMyProperty(PropertyService.USER_ID));
        teamIdTextField.setText(propertyService.getMyProperty(PropertyService.TEAM_ID));
    }

    @Override
    public void disposeUIResources() {
        panel = null;
        userIdTextField = null;
        teamIdTextField = null;
    }
}
