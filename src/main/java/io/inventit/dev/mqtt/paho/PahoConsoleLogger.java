/*
 * Copyright (c) 2014 Inventit Inc.
 */
package io.inventit.dev.mqtt.paho;

import java.text.MessageFormat;
import java.util.ResourceBundle;

import org.eclipse.paho.client.mqttv3.logging.Logger;
import org.eclipse.paho.client.mqttv3.logging.LoggerFactory;

public class PahoConsoleLogger implements Logger {

	public static void enableLog() {
		LoggerFactory.setLogger(PahoConsoleLogger.class.getName());
	}

	private ResourceBundle messageCatalog;

	@Override
	public void initialise(ResourceBundle messageCatalog, String loggerID,
			String resourceName) {
		this.messageCatalog = messageCatalog;
	}

	@Override
	public void setResourceName(String logContext) {
	}

	@Override
	public boolean isLoggable(int level) {
		return true;
	}

	@Override
	public void severe(String sourceClass, String sourceMethod, String msg) {
		System.err.println("[SEVERE]" + sourceClass + ":" + sourceMethod + ":"
				+ msg);
	}

	@Override
	public void severe(String sourceClass, String sourceMethod, String msg,
			Object[] inserts) {
		System.err.println("[SEVERE]" + sourceClass + ":" + sourceMethod + ":"
				+ formatMessage(msg, inserts));
	}

	@Override
	public void severe(String sourceClass, String sourceMethod, String msg,
			Object[] inserts, Throwable thrown) {
		System.err.println("[SEVERE]" + sourceClass + ":" + sourceMethod + ":"
				+ formatMessage(msg, inserts));
		thrown.printStackTrace();
	}

	@Override
	public void warning(String sourceClass, String sourceMethod, String msg) {
		System.err.println("[WARN]" + sourceClass + ":" + sourceMethod);
	}

	@Override
	public void warning(String sourceClass, String sourceMethod, String msg,
			Object[] inserts) {
		System.err.println("[WARN]" + sourceClass + ":" + sourceMethod + ":"
				+ formatMessage(msg, inserts));
	}

	@Override
	public void warning(String sourceClass, String sourceMethod, String msg,
			Object[] inserts, Throwable thrown) {
		System.err.println("[WARN]" + sourceClass + ":" + sourceMethod + ":"
				+ formatMessage(msg, inserts));
		thrown.printStackTrace();
	}

	@Override
	public void info(String sourceClass, String sourceMethod, String msg) {
		System.err.println("[INFO]" + sourceClass + ":" + sourceMethod + ":"
				+ msg);
	}

	@Override
	public void info(String sourceClass, String sourceMethod, String msg,
			Object[] inserts) {
		System.err.println("[INFO]" + sourceClass + ":" + sourceMethod + ":"
				+ formatMessage(msg, inserts));
	}

	@Override
	public void info(String sourceClass, String sourceMethod, String msg,
			Object[] inserts, Throwable thrown) {
		System.err.println("[INFO]" + sourceClass + ":" + sourceMethod + ":"
				+ formatMessage(msg, inserts));
		thrown.printStackTrace();
	}

	@Override
	public void config(String sourceClass, String sourceMethod, String msg) {
		System.err.println("[CONFIG]" + sourceClass + ":" + sourceMethod + ":"
				+ msg);
	}

	@Override
	public void config(String sourceClass, String sourceMethod, String msg,
			Object[] inserts) {
		System.err.println("[CONFIG]" + sourceClass + ":" + sourceMethod + ":"
				+ formatMessage(msg, inserts));
	}

	@Override
	public void config(String sourceClass, String sourceMethod, String msg,
			Object[] inserts, Throwable thrown) {
		System.err.println("[CONFIG]" + sourceClass + ":" + sourceMethod + ":"
				+ formatMessage(msg, inserts));
		thrown.printStackTrace();
	}

	@Override
	public void fine(String sourceClass, String sourceMethod, String msg) {
		System.err.println("[FINE]" + sourceClass + ":" + sourceMethod + ":"
				+ msg);
	}

	@Override
	public void fine(String sourceClass, String sourceMethod, String msg,
			Object[] inserts) {
		System.err.println("[FINE]" + sourceClass + ":" + sourceMethod + ":"
				+ formatMessage(msg, inserts));
	}

	@Override
	public void fine(String sourceClass, String sourceMethod, String msg,
			Object[] inserts, Throwable ex) {
		System.err.println("[FINE]" + sourceClass + ":" + sourceMethod + ":"
				+ formatMessage(msg, inserts));
		ex.printStackTrace();
	}

	@Override
	public void finer(String sourceClass, String sourceMethod, String msg) {
		System.err.println("[FINER]" + sourceClass + ":" + sourceMethod + ":"
				+ msg);
	}

	@Override
	public void finer(String sourceClass, String sourceMethod, String msg,
			Object[] inserts) {
		System.err.println("[FINER]" + sourceClass + ":" + sourceMethod + ":"
				+ formatMessage(msg, inserts));
	}

	@Override
	public void finer(String sourceClass, String sourceMethod, String msg,
			Object[] inserts, Throwable ex) {
		System.err.println("[FINER]" + sourceClass + ":" + sourceMethod + ":"
				+ formatMessage(msg, inserts));
		ex.printStackTrace();
	}

	@Override
	public void finest(String sourceClass, String sourceMethod, String msg) {
		System.err.println("[FINEST]" + sourceClass + ":" + sourceMethod + ":"
				+ msg);
	}

	@Override
	public void finest(String sourceClass, String sourceMethod, String msg,
			Object[] inserts) {
		System.err.println("[FINEST]" + sourceClass + ":" + sourceMethod + ":"
				+ formatMessage(msg, inserts));
	}

	@Override
	public void finest(String sourceClass, String sourceMethod, String msg,
			Object[] inserts, Throwable ex) {
		System.err.println("[FINEST]" + sourceClass + ":" + sourceMethod + ":"
				+ formatMessage(msg, inserts));
		ex.printStackTrace();
	}

	@Override
	public void log(int level, String sourceClass, String sourceMethod,
			String msg, Object[] inserts, Throwable thrown) {
		switch (level) {
		case Logger.CONFIG:
			config(sourceClass, sourceMethod, msg, inserts, thrown);
			break;
		default:
		case Logger.FINE:
			fine(sourceClass, sourceMethod, msg, inserts, thrown);
			break;
		case Logger.FINER:
			finer(sourceClass, sourceMethod, msg, inserts, thrown);
			break;
		case Logger.FINEST:
			finest(sourceClass, sourceMethod, msg, inserts, thrown);
			break;
		case Logger.INFO:
			info(sourceClass, sourceMethod, msg, inserts, thrown);
			break;
		case Logger.WARNING:
			warning(sourceClass, sourceMethod, msg, inserts, thrown);
			break;
		case Logger.SEVERE:
			severe(sourceClass, sourceMethod, msg, inserts, thrown);
			break;
		}
	}

	@Override
	public void trace(int level, String sourceClass, String sourceMethod,
			String msg, Object[] inserts, Throwable thrown) {
		log(level, sourceClass, sourceMethod, msg, inserts, thrown);
	}

	@Override
	public String formatMessage(String msg, Object[] inserts) {
		if (!messageCatalog.containsKey(msg)) {
			return MessageFormat.format(msg, inserts);
		}
		final String message = messageCatalog.getString(msg);
		if (inserts == null) {
			return message;
		} else {
			return MessageFormat.format(message, inserts);
		}
	}

	@Override
	public void dumpTrace() {
	}

}
