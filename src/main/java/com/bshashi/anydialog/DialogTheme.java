package com.bshashi.anydialog;

import java.util.HashMap;
import java.util.Map;

public class DialogTheme {
    private String name;
    private String backgroundColor;
    private String textColor;
    private String buttonColor;
    private String buttonHoverColor;
    private String borderColor;
    private Map<String, String> customProperties;
    
    public DialogTheme() {
        this("default");
    }
    
    public DialogTheme(String name) {
        this.name = name;
        this.customProperties = new HashMap<>();
        setDefaultTheme();
    }
    
    private void setDefaultTheme() {
        this.backgroundColor = "#FFFFFF";
        this.textColor = "#333333";
        this.buttonColor = "#007AFF";
        this.buttonHoverColor = "#0056B3";
        this.borderColor = "#E0E0E0";
    }
    
    public static DialogTheme darkTheme() {
        DialogTheme theme = new DialogTheme("dark");
        theme.setBackgroundColor("#1E1E1E");
        theme.setTextColor("#FFFFFF");
        theme.setButtonColor("#0A84FF");
        theme.setButtonHoverColor("#0056B3");
        theme.setBorderColor("#383838");
        return theme;
    }
    
    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getBackgroundColor() { return backgroundColor; }
    public void setBackgroundColor(String backgroundColor) { this.backgroundColor = backgroundColor; }
    
    public String getTextColor() { return textColor; }
    public void setTextColor(String textColor) { this.textColor = textColor; }
    
    public String getButtonColor() { return buttonColor; }
    public void setButtonColor(String buttonColor) { this.buttonColor = buttonColor; }
    
    public String getButtonHoverColor() { return buttonHoverColor; }
    public void setButtonHoverColor(String buttonHoverColor) { this.buttonHoverColor = buttonHoverColor; }
    
    public String getBorderColor() { return borderColor; }
    public void setBorderColor(String borderColor) { this.borderColor = borderColor; }
    
    public void setCustomProperty(String key, String value) {
        customProperties.put(key, value);
    }
    
    public String getCustomProperty(String key) {
        return customProperties.get(key);
    }
}