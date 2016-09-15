package com.console.util;

/**
 * Created by exfaff on 15/09/2016.
 */
public class MessageUtil {

    public String getMsg(String msg) {
        DateUtil util = new DateUtil();
        StringBuilder builder = new StringBuilder("[ ");
        builder.append(util.getNow()).append(" ] ")
                .append(msg);
        return builder.toString();
    }
}
