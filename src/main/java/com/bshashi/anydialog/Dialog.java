package com.bshashi.anydialog;

import java.util.*;
import java.time.LocalDateTime;

public class Dialog {
    private String id;
    private String title;
    private String message;
    private DialogType type;
    private List<DialogOption> options;
    private Map<String, Object> metadata;
    private LocalDateTime createdAt;
    private boolean isModal;
    private boolean cancellable;
    private int timeoutSeconds;
    private String iconPath;
    
    public Dialog() {
        this.id = UUID.randomUUID().toString();
        this.options = new ArrayList<>();
        this.metadata = new HashMap<>();
        this.createdAt = LocalDateTime.now();
        this.isModal = true;
        this.cancellable = true;
        this.timeoutSeconds = 0;
    }
    
    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    
    public DialogType getType() { return type; }
    public void setType(DialogType type) { this.type = type; }
    
    public List<DialogOption> getOptions() { return options; }
    public void setOptions(List<DialogOption> options) { this.options = options; }
    
    public Map<String, Object> getMetadata() { return metadata; }
    public void setMetadata(Map<String, Object> metadata) { this.metadata = metadata; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public boolean isModal() { return isModal; }
    public void setModal(boolean modal) { isModal = modal; }
    
    public boolean isCancellable() { return cancellable; }
    public void setCancellable(boolean cancellable) { this.cancellable = cancellable; }
    
    public int getTimeoutSeconds() { return timeoutSeconds; }
    public void setTimeoutSeconds(int timeoutSeconds) { this.timeoutSeconds = timeoutSeconds; }
    
    public String getIconPath() { return iconPath; }
    public void setIconPath(String iconPath) { this.iconPath = iconPath; }
    
    public void addOption(DialogOption option) {
        this.options.add(option);
    }
    
    public void addMetadata(String key, Object value) {
        this.metadata.put(key, value);
    }
    
    public Object getMetadata(String key) {
        return this.metadata.get(key);
    }
}