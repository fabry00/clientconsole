package com.console.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Date;

/**
 *
 * @author fabry
 */
public class DateUtil {

    public String getNow() {
        /*DateTimeFormatter formatter
                = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
                //.withLocale(Locale.UK)
                .withZone(ZoneId.systemDefault());

        Instant instant = Instant.now(); // Current date-time in UTC.
        String output = formatter.format(instant);
        return output;*/

        LocalDateTime datetime = LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC);
        String formatted = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss").format(datetime);
        return formatted;
    }

    public Instant getNowInstant() {
        return Instant.now();
    }

    public Date getNowDate() {
        return new Date();
    }
}
