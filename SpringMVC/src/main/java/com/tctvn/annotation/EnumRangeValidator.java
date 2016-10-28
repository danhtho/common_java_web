package com.tctvn.annotation;

import java.lang.reflect.InvocationTargetException;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;

import com.tctvn.utils.EnumUtils;
import com.tctvn.utils.MessageUtil;

public class EnumRangeValidator implements ConstraintValidator<EnumRange, String>{
    private String message;
    private Class<? extends Enum<?>> enumClass;
    private String[] arg;
    
    @Override
    public void initialize(EnumRange arg0) {
        this.enumClass = arg0.enumClass();
        this.arg = arg0.arg();
        this.message = arg0.message();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext arg1) {
        try {
            if (!isEnumRange(value, enumClass)) {
                arg1.disableDefaultConstraintViolation();
                arg1.buildConstraintViolationWithTemplate(MessageUtil.getMessage(message, arg))
                    .addConstraintViolation();
                return false;
            }
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean isEnumRange(final String str, final Class<? extends Enum<?>> enumClass) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (StringUtils.isBlank(str)) {
            return true;
        }
        if (!isInteger(str)) {
            return false;
        }
        Enum<?> enumConstant = EnumUtils.valueOfClassic(enumClass, str);
        return enumConstant != null;
    }
    
    public static boolean isInteger(final String str) {
        if (StringUtils.isBlank(str)) {
            return true;
        }
        try {
            toInt(str);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
    private static int toInt(final String str) {
        return Integer.parseInt(str);
    }
}
