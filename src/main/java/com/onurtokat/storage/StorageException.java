package com.onurtokat.storage;

/**
 * StorageException provides overloaded exception methods which will be
 * thrown when file does not exist. It extends RuntimeException
 *
 * @author onurtokat
 */
public class StorageException extends RuntimeException {

    /**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

    /**
     * StorageException method gives message when any RuntimeExceptions
     * occur
     *
     * @param message Exception message to be clear about exception
     */
	public StorageException(String message) {
        super(message);
    }

    /**
     * StorageException method gives message when any RuntimeExceptions
     *occur
     *
     * @param message Exception message to be clear about exception
     * @param cause   default java API exception stack trace stream
     */
    public StorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
