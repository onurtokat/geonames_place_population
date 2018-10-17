package com.onurtokat.storage;

/**
 * StorageFileNotFoundException provides overloaded exception methods
 * which will be thrown when file does not exist. It extends StorageException
 *
 * @author onurtokat
 */
public class StorageFileNotFoundException extends StorageException {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * StorageFileNotFoundException method gives message when FileNotException
     * occurs
     *
     * @param message Exception message to be clear about exception
     */
    public StorageFileNotFoundException(String message) {
        super(message);
    }

    /**
     * StorageFileNotFoundException method gives message when FileNotException
     *
     * @param message Exception message to be clear about exception
     * @param cause   default java API exception stack trace stream
     */
    public StorageFileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
