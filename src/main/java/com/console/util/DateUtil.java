package com.console.util;

import java.time.Instant;

/**
 *
 * @author fabry
 */
public class DateUtil {

    public String getNow() {
        Instant instant = Instant.now (); // Current date-time in UTC.
        return instant.toString ();
    }
}
