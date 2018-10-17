package com.onurtokat.init;

/**
 * PlaceDataInitFileNotFoundException provides overloaded exception methods which will be
 * thrown when file does not exist. It extends RuntimeException
 *
 * @author onurtokat
 */
public class PlaceDataInitFileNotFoundException extends PlaceDataInitException {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * PlaceDataInitFileNotFoundException method gives message
     * when any FileNotFoundException occur
     *
     * @param message Exception message to be clear about exception
     */
    public PlaceDataInitFileNotFoundException(String message) {
        super(message);
    }

    /**
     * PlaceDataInitFileNotFoundException method gives message when any RuntimeExceptions
     * occur
     *
     * @param message Exception message to be clear about exception
     * @param cause   default java API exception stack trace stream
     */
    public PlaceDataInitFileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
