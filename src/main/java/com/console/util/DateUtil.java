package com.console.util;

import java.text.SimpleDateFormat;
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

    private static final String FORMAT = "yyyy-MM-dd hh:mm:ss";

    public String getNow() {
        LocalDateTime datetime = LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC);
        String formatted = DateTimeFormatter.ofPattern(FORMAT).format(datetime);
        return formatted;
    }

    public String formatDate(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat(FORMAT);
        return formatter.format(date);
    }

    public Date getNowDate() {
        return new Date();
    }
}
