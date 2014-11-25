package com.utoronto.timemng.app.constants;

/**
 * 25/11/2014 12:18.
 */
public final class Constants {

    public static final int REQUEST_EXIT = 1000;

    /**
     * Don't want anything to be able to instantiate this class.
     */
    private Constants () {
        super();
        throw new AssertionError();
    }
}
