package com.bshashi.anydialog;

import com.bshashi.anydialog.exceptions.DialogNotFoundException;
import com.bshashi.anydialog.utils.DialogLogger;
import com.bshashi.anydialog.utils.DialogValidator;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicReference;

public class DialogManager {
	private static DialogManager instance;
	private final Map<String, Dialog> activeDialogs;
	private final DialogQueue dialogQueue;
	private final DialogHistory dialogHistory;
	private final List<DialogListener> globalListeners;
	private final ScheduledExecutorService scheduledExecutorService;
	private final ExecutorService executorService;
	private final DialogLogger logger;
	private DialogTheme currentTheme;
	
	private DialogManager() {
		this.activeDialogs = new ConcurrentHashMap<>();
		this.dialogQueue = new DialogQueue();
		this.dialogHistory = new DialogHistory();
		this.globalListeners = new CopyOnWriteArrayList<>();
		this.scheduledExecutorService = Executors.newScheduledThreadPool(2);
		this.executorService = Executors.newCachedThreadPool();
		this.logger = new DialogLogger(DialogManager.class);
		this.currentTheme = new DialogTheme();
	}
	
	public static synchronized DialogManager getInstance() {
		if (instance == null) {
			instance = new DialogManager();
		}
		return instance;
	}
	
	public String showDialog(Dialog dialog) {
		return showDialog(dialog, null);
	}
	
	public String showDialog(Dialog dialog, List<DialogListener> specificListeners) {
		try {
			DialogValidator.validateDialog(dialog);
			
			String dialogId = dialog.getId();
			activeDialogs.put(dialogId, dialog);
			
			// Notify listeners
			notifyDialogShown(dialog);
			if (specificListeners != null) {
				specificListeners.forEach(l -> l.onDialogShown(dialog));
			}
			
			// Handle timeout
			if (dialog.getTimeoutSeconds() > 0) {
				scheduleAutoDismiss(dialog);
			}
			
			logger.info("Dialog shown: " + dialog.getTitle());
			dialogHistory.addToHistory(dialog);
			
			return dialogId;
		} catch (Exception e) {
			logger.error("Error showing dialog", e);
			notifyDialogError(dialog, e);
			return null;
		}
	}
	
	public void dismissDialog(String dialogId) throws DialogNotFoundException {
		Dialog dialog = activeDialogs.get(dialogId);
		if (dialog == null) {
			throw new DialogNotFoundException("Dialog not found with ID: " + dialogId);
		}
		
		activeDialogs.remove(dialogId);
		notifyDialogDismissed(dialog);
		logger.info("Dialog dismissed: " + dialog.getTitle());
		
		// Show next dialog in queue
		showNextQueuedDialog();
	}
	
	public void selectOption(String dialogId, DialogOption option) throws DialogNotFoundException {
		Dialog dialog = activeDialogs.get(dialogId);
		if (dialog == null) {
			throw new DialogNotFoundException("Dialog not found with ID: " + dialogId);
		}
		
		notifyOptionSelected(dialog, option);
		
		if (option.getOnClick() != null) {
			executorService.submit(() -> option.getOnClick().accept(dialog));
		}
		
		if (dialog.isModal()) {
			dismissDialog(dialogId);
		}
	}
	
	public void queueDialog(Dialog dialog) {
		dialogQueue.addToQueue(dialog);
		if (activeDialogs.isEmpty()) {
			showNextQueuedDialog();
		}
	}
	
	private void showNextQueuedDialog() {
		Dialog nextDialog = dialogQueue.getNextDialog();
		if (nextDialog != null) {
			showDialog(nextDialog);
		}
	}
	
	private void scheduleAutoDismiss(Dialog dialog) {
		scheduledExecutorService.schedule(() -> {
			try {
				if (activeDialogs.containsKey(dialog.getId())) {
					dismissDialog(dialog.getId());
					logger.info("Dialog auto-dismissed after timeout: " + dialog.getTitle());
				}
			} catch (DialogNotFoundException e) {
				// Dialog already dismissed
			}
		}, dialog.getTimeoutSeconds(), TimeUnit.SECONDS);
	}
	
	public List<Dialog> getActiveDialogs() {
		return new ArrayList<>(activeDialogs.values());
	}
	
	public Dialog getActiveDialog(String id) {
		return activeDialogs.get(id);
	}
	
	public DialogHistory getDialogHistory() {
		return dialogHistory;
	}
	
	public DialogQueue getDialogQueue() {
		return dialogQueue;
	}
	
	public void addGlobalListener(DialogListener listener) {
		globalListeners.add(listener);
	}
	
	public void removeGlobalListener(DialogListener listener) {
		globalListeners.remove(listener);
	}
	
	public void setTheme(DialogTheme theme) {
		this.currentTheme = theme;
		logger.info("Theme applied: " + theme.getName());
	}
	
	public DialogTheme getCurrentTheme() {
		return currentTheme;
	}
	
	public void clearAllDialogs() {
		activeDialogs.clear();
		dialogQueue.clear();
		logger.info("All dialogs cleared");
	}
	
	public void shutdown() {
		executorService.shutdown();
		scheduledExecutorService.shutdown();
		try {
			if (!executorService.awaitTermination(5, TimeUnit.SECONDS)) {
				executorService.shutdownNow();
			}
			if (!scheduledExecutorService.awaitTermination(5, TimeUnit.SECONDS)) {
				scheduledExecutorService.shutdownNow();
			}
		} catch (InterruptedException e) {
			executorService.shutdownNow();
			scheduledExecutorService.shutdownNow();
			Thread.currentThread().interrupt();
		}
	}
	
	private void notifyDialogShown(Dialog dialog) {
		globalListeners.forEach(l -> l.onDialogShown(dialog));
	}
	
	private void notifyDialogDismissed(Dialog dialog) {
		globalListeners.forEach(l -> l.onDialogDismissed(dialog));
	}
	
	private void notifyOptionSelected(Dialog dialog, DialogOption option) {
		globalListeners.forEach(l -> l.onOptionSelected(dialog, option));
	}
	
	private void notifyDialogCancelled(Dialog dialog) {
		globalListeners.forEach(l -> l.onDialogCancelled(dialog));
	}
	
	private void notifyDialogError(Dialog dialog, Exception error) {
		globalListeners.forEach(l -> l.onDialogError(dialog, error));
	}
}
