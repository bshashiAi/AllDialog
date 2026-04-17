package com.bshashi.anydialog;

import java.util.concurrent.*;
import java.util.*;

public class DialogQueue {
    private final Queue<Dialog> queue;
    private final int maxSize;
    
    public DialogQueue() {
        this(100);
    }
    
    public DialogQueue(int maxSize) {
        this.queue = new ConcurrentLinkedQueue<>();
        this.maxSize = maxSize;
    }
    
    public boolean addToQueue(Dialog dialog) {
        if (queue.size() >= maxSize) {
            return false;
        }
        return queue.offer(dialog);
    }
    
    public Dialog getNextDialog() {
        return queue.poll();
    }
    
    public Dialog peekNextDialog() {
        return queue.peek();
    }
    
    public int getQueueSize() {
        return queue.size();
    }
    
    public boolean isEmpty() {
        return queue.isEmpty();
    }
    
    public void clear() {
        queue.clear();
    }
    
    public List<Dialog> getAllDialogs() {
        return new ArrayList<>(queue);
    }
}