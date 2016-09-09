package com.console.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 *
 * @author user
 */
public class DateUtil {

    /**
     * The date pattern that is used for conversion. Change as you wish.
     */
    private static final String DATE_PATTERN = "dd.MM.yyyy";

    /**
     * The date formatter.
     */
    private static final DateTimeFormatter DATE_FORMATTER
            = DateTimeFormatter.ofPattern(DATE_PATTERN);

    /**
     * Returns the given date as a well formatted String. The above defined
     * {@link DateUtil#DATE_PATTERN} is used.
     *
     * @param date the date to be returned as a string
     * @return formatted string
     */
    public String format(LocalDate date) {
        if (date == null) {
            return null;
        }
        return DATE_FORMATTER.format(date);
    }

    /**
     * Converts a String in the format of the defined
     * {@link DateUtil#DATE_PATTERN} to a {@link LocalDate} object.
     *
     * Returns null if the String could not be converted.
     *
     * @param dateString the date as String
     * @return the date object or null if it could not be converted
     */
    public LocalDate parse(String dateString) {
        try {
            return DATE_FORMATTER.parse(dateString, LocalDate::from);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    /**
     * Checks the String whether it is a valid date.
     *
     * @param dateString
     * @return true if the String is a valid date
     */
    public boolean validDate(String dateString) {
        // Try to parse the String.
        return new DateUtil().parse(dateString) != null;
    }

    /**
     * Adapter (for JAXB) to convert between the LocalDate and the ISO 8601
     * String representation of the date such as '2012-12-03'.
     *
     * @author Marco Jakob
     */
    public static class LocalDateAdapter extends XmlAdapter<String, LocalDate> {

        @Override
        public LocalDate unmarshal(String v) throws Exception {
            return LocalDate.parse(v);
        }

        @Override
        public String marshal(LocalDate v) throws Exception {
            return v.toString();
        }
    }
}
