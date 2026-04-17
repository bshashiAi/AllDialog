package com.bshashi.anydialog;

public interface DialogListener {
    void onDialogShown(Dialog dialog);
    void onDialogDismissed(Dialog dialog);
    void onOptionSelected(Dialog dialog, DialogOption option);
    void onDialogCancelled(Dialog dialog);
    void onDialogError(Dialog dialog, Exception error);
}