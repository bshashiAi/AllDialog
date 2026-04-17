package com.bshashi.anydialog;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class DialogBuilder {
    private Dialog dialog;
    private List<DialogListener> listeners;
    
    public DialogBuilder() {
        this.dialog = new Dialog();
        this.listeners = new ArrayList<>();
    }
    
    public DialogBuilder setTitle(String title) {
        dialog.setTitle(title);
        return this;
    }
    
    public DialogBuilder setMessage(String message) {
        dialog.setMessage(message);
        return this;
    }
    
    public DialogBuilder setType(DialogType type) {
        dialog.setType(type);
        return this;
    }
    
    public DialogBuilder addOption(String text, Consumer<Dialog> onClick) {
        DialogOption option = new DialogOption(text);
        option.setOnClick(onClick);
        dialog.addOption(option);
        return this;
    }
    
    public DialogBuilder addOption(DialogOption option) {
        dialog.addOption(option);
        return this;
    }
    
    public DialogBuilder setModal(boolean modal) {
        dialog.setModal(modal);
        return this;
    }
    
    public DialogBuilder setCancellable(boolean cancellable) {
        dialog.setCancellable(cancellable);
        return this;
    }
    
    public DialogBuilder setTimeout(int seconds) {
        dialog.setTimeoutSeconds(seconds);
        return this;
    }
    
    public DialogBuilder setIcon(String iconPath) {
        dialog.setIconPath(iconPath);
        return this;
    }
    
    public DialogBuilder addMetadata(String key, Object value) {
        dialog.addMetadata(key, value);
        return this;
    }
    
    public DialogBuilder addListener(DialogListener listener) {
        this.listeners.add(listener);
        return this;
    }
    
    public Dialog build() {
        validateDialog();
        return dialog;
    }
    
    private void validateDialog() {
        if (dialog.getTitle() == null || dialog.getTitle().trim().isEmpty()) {
            throw new IllegalStateException("Dialog title cannot be empty");
        }
        if (dialog.getMessage() == null || dialog.getMessage().trim().isEmpty()) {
            throw new IllegalStateException("Dialog message cannot be empty");
        }
        if (dialog.getType() == null) {
            dialog.setType(DialogType.INFO);
        }
    }
    
    public List<DialogListener> getListeners() {
        return listeners;
    }
}