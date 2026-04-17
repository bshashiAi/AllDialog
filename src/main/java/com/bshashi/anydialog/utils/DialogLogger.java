package com.bshashi.anydialog.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DialogLogger {
    private final Logger logger;
    
    public DialogLogger(Class<?> clazz) {
        this.logger = LoggerFactory.getLogger(clazz);
    }
    
    public void info(String message) {
        logger.info("[DialogLib] " + message);
    }
    
    public void error(String message) {
        logger.error("[DialogLib] " + message);
    }
    
    public void error(String message, Throwable throwable) {
        logger.error("[DialogLib] " + message, throwable);
    }
    
    public void warn(String message) {
        logger.warn("[DialogLib] " + message);
    }
    
    public void debug(String message) {
        logger.debug("[DialogLib] " + message);
    }
}