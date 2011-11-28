package nl.profict.albero.utilities;

import org.slf4j.LoggerFactory;

/**
 * Wraps an {@link org.slf4j.Logger SLF4J logger} to allow supplying a log message in parts. A logger can be obtained by
 * using the {@link #get(Class) get method}.
 *
 * @author levi_h
 */
public class Logger {
	private org.slf4j.Logger logger;

	/**
	 * Creates a logger.
	 *
	 * @param logger the underlying logger
	 */
	private Logger(org.slf4j.Logger logger) {
		this.logger = logger;
	}

	/**
	 * Logs a debug message.
	 *
	 * @param messageParts the parts of the log message
	 */
	public void debug(Object... messageParts) {
		if (logger.isDebugEnabled()) {
			logger.debug(StringUtilities.join(messageParts));
		}
	}

	/**
	 * Logs an exception with an accompanying debug message.
	 *
	 * @param exception the exception to log
	 * @param messageParts the parts of the log message
	 */
	public void debug(Throwable exception, Object... messageParts) {
		if (logger.isDebugEnabled()) {
			logger.debug(StringUtilities.join(messageParts), exception);
		}
	}

	/**
	 * Logs an information message.
	 *
	 * @param messageParts the parts of the log message
	 */
	public void info(Object... messageParts) {
		if (logger.isInfoEnabled()) {
			logger.info(StringUtilities.join(messageParts));
		}
	}

	/**
	 * Logs an exception with an accompanying information message.
	 *
	 * @param exception the exception to log
	 * @param messageParts the parts of the log message
	 */
	public void info(Throwable exception, Object... messageParts) {
		if (logger.isInfoEnabled()) {
			logger.info(StringUtilities.join(messageParts), exception);
		}
	}

	/**
	 * Logs a warning message.
	 *
	 * @param messageParts the parts of the log message
	 */
	public void warn(Object... messageParts) {
		if (logger.isWarnEnabled()) {
			logger.warn(StringUtilities.join(messageParts));
		}
	}

	/**
	 * Logs an exception with an accompanying warning message.
	 *
	 * @param exception the exception to log
	 * @param messageParts the parts of the log message
	 */
	public void warn(Throwable exception, Object... messageParts) {
		if (logger.isWarnEnabled()) {
			logger.warn(StringUtilities.join(messageParts), exception);
		}
	}

	/**
	 * Logs an error message.
	 *
	 * @param messageParts the parts of the log message
	 */
	public void error(Object... messageParts) {
		if (logger.isErrorEnabled()) {
			logger.error(StringUtilities.join(messageParts));
		}
	}

	/**
	 * Logs an exception with an accompanying error message.
	 *
	 * @param exception the exception to log
	 * @param messageParts the parts of the log message
	 */
	public void error(Throwable exception, Object... messageParts) {
		if (logger.isErrorEnabled()) {
			logger.error(StringUtilities.join(messageParts), exception);
		}
	}

	/**
	 * Returns a logger for a certain type.
	 *
	 * @param type the type to get a logger for
	 * @return a logger for the given type
	 */
	public static Logger get(Class<?> type) {
		return new Logger(LoggerFactory.getLogger(type));
	}
}