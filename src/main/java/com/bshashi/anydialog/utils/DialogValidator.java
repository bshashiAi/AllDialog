package com.bshashi.anydialog.utils;

import com.bshashi.anydialog.Dialog;

public class DialogValidator {
    
    public static void validateDialog(Dialog dialog) {
        if (dialog == null) {
            throw new IllegalArgumentException("Dialog cannot be null");
        }
        
        if (dialog.getTitle() == null || dialog.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Dialog title cannot be empty");
        }
        
        if (dialog.getMessage() == null || dialog.getMessage().trim().isEmpty()) {
            throw new IllegalArgumentException("Dialog message cannot be empty");
        }
        
        if (dialog.getType() == null) {
            throw new IllegalArgumentException("Dialog type cannot be null");
        }
    }
    
    public static boolean isValidDialog(Dialog dialog) {
        try {
            validateDialog(dialog);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}