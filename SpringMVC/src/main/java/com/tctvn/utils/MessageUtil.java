package com.tctvn.utils;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
public class MessageUtil {

    private static MessageSource messageSource;

    @Autowired
    public MessageUtil(MessageSource messageSource) {
        MessageUtil.messageSource = messageSource;
    }

    // su dung method nay trong truong hop message co yeu cau truyen parameter
    public static String getMessage(String messageCode, Object[] param) {
        messageCode = messageCode.replace("{", "").replace("}", "");
        return messageSource.getMessage(messageCode, param, new Locale("us",
                "US"));
    }

    // su dung method nay trong truong hop message khong yeu cau parameter
    public static String getMessage(String messageCode) {
        messageCode = messageCode.replace("{", "").replace("}", "");
        return messageSource.getMessage(messageCode, new Object[] { "" },
                new Locale("us", "US"));

    }
}
