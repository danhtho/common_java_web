package com.tctvn.annotation;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;

import com.tctvn.utils.MessageUtil;

public class DateFormatValidator implements ConstraintValidator<DateFormat, String>{
    private String format;
    private String message;
    private String[] arg;
    
    @Override
    public void initialize(DateFormat arg0) {
        this.format = arg0.dateFormat();
        this.message = arg0.message();
        this.arg = arg0.arg();
    }

    @Override
    public boolean isValid(String dateValue, ConstraintValidatorContext arg1) {
        if (StringUtils.isBlank(dateValue)) {
            return true;
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            sdf.setLenient(false);
            try {
                //if not valid, it will throw ParseException
                sdf.parse(dateValue);
                return true;
            } catch (ParseException e) {
                arg1.disableDefaultConstraintViolation();
                arg1.buildConstraintViolationWithTemplate(MessageUtil.getMessage(message, arg)).addConstraintViolation();
                return false;
            }
        }
    }

}
