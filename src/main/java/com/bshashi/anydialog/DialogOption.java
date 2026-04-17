package com.bshashi.anydialog;

import java.util.function.Consumer;

public class DialogOption {
    private String text;
    private String value;
    private boolean defaultOption;
    private boolean destructive;
    private Consumer<Dialog> onClick;
    private String iconPath;
    
    public DialogOption(String text) {
        this.text = text;
        this.value = text.toLowerCase().replace(" ", "_");
        this.defaultOption = false;
        this.destructive = false;
    }
    
    public DialogOption(String text, String value) {
        this.text = text;
        this.value = value;
        this.defaultOption = false;
        this.destructive = false;
    }
    
    // Getters and Setters
    public String getText() { return text; }
    public void setText(String text) { this.text = text; }
    
    public String getValue() { return value; }
    public void setValue(String value) { this.value = value; }
    
    public boolean isDefaultOption() { return defaultOption; }
    public DialogOption setDefaultOption(boolean defaultOption) {
        this.defaultOption = defaultOption;
        return this;
    }
    
    public boolean isDestructive() { return destructive; }
    public DialogOption setDestructive(boolean destructive) {
        this.destructive = destructive;
        return this;
    }
    
    public Consumer<Dialog> getOnClick() { return onClick; }
    public DialogOption setOnClick(Consumer<Dialog> onClick) {
        this.onClick = onClick;
        return this;
    }
    
    public String getIconPath() { return iconPath; }
    public DialogOption setIconPath(String iconPath) {
        this.iconPath = iconPath;
        return this;
    }
}