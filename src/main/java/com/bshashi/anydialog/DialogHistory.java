package com.bshashi.anydialog;

import java.util.*;
import java.util.stream.Collectors;

public class DialogHistory {
    private final List<Dialog> history;
    private final int maxHistorySize;
    
    public DialogHistory() {
        this(1000);
    }
    
    public DialogHistory(int maxHistorySize) {
        this.history = Collections.synchronizedList(new ArrayList<>());
        this.maxHistorySize = maxHistorySize;
    }
    
    public void addToHistory(Dialog dialog) {
        history.add(dialog);
        if (history.size() > maxHistorySize) {
            history.remove(0);
        }
    }
    
    public List<Dialog> getHistory() {
        return new ArrayList<>(history);
    }
    
    public List<Dialog> getHistoryByType(DialogType type) {
        return history.stream()
                .filter(d -> d.getType() == type)
                .collect(Collectors.toList());
    }
    
    public void clearHistory() {
        history.clear();
    }
    
    public Dialog getLastDialog() {
        return history.isEmpty() ? null : history.get(history.size() - 1);
    }
    
    public int getHistorySize() {
        return history.size();
    }
}