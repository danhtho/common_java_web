package com.tctvn.utils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.BeanUtils;

public class EnumUtils {
    private static Map<Class<?>, Object[]> enumConstantsTable = new HashMap<Class<?>, Object[]>();
    
    public static <T extends InterfaceEnum> T valueOf(final Class<T> enumType, final Object value) {
        int intValue;
        if (value instanceof Number) {
            intValue = ((Number)value).intValue();
        } else if (value instanceof String) {
            intValue = NumberUtils.toInt((String)value);
            if (!StringUtils.equals((String)value, Integer.toString(intValue))) {
                return null;
            }
        } else {
            return null;
        }
        T[] enumConstants;
        if (enumType.isEnum()) {
            enumConstants = enumType.getEnumConstants();
            for (T enumConstant : enumConstants) {
                int enumValue = enumConstant.getValue();
                if (intValue == enumValue) {
                    return enumConstant;
                }
            }
        } else {
            enumConstants = zcoGetEnumConstants(enumType);
            for (T enumConstant : enumConstants) {
                int enumValue = enumConstant.getValue();
                if (intValue == enumValue) {
                    return enumConstant;
                }
            }
        }
        return null;
    }
    
    @SuppressWarnings("unchecked")
    public static <T> T valueOfClassic(final Class<T> enumType, final Object value) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (InterfaceEnum.class.isAssignableFrom(enumType)) {
            return (T)valueOf((Class<? extends InterfaceEnum>)enumType, value);
        }
        PropertyDescriptor propertyDesc = BeanUtils.getPropertyDescriptor(enumType, "value");
        T[] enumConstants;
        if (enumType.isEnum()) {
            enumConstants = enumType.getEnumConstants();
            for (T enumConstant : enumConstants) {
                Object enumValue = propertyDesc.getReadMethod().invoke(enumConstant);
                if (value == null && enumValue == null) {
                    return enumConstant;
                } else {
                    if (value instanceof Integer) {
                        if (enumValue.equals(value)) {
                            return enumConstant;
                        }
                    } else {
                        String enumStr = String.valueOf(enumValue);
                        String str = String.valueOf(value);
                        if (StringUtils.equals(enumStr, str)) {
                            return enumConstant;
                        }
                    }
                }
            }
        } else {
            enumConstants = zcoGetEnumConstants(enumType);
            for (T enumConstant : enumConstants) {
                Object enumValue = propertyDesc.getReadMethod().invoke(enumConstant);
                if (value == null && enumValue == null) {
                    return enumConstant;
                } else if (value instanceof Integer) {
                    if (enumValue.equals(value)) {
                        return enumConstant;
                    }
                } else {
                    String enumStr = String.valueOf(enumValue);
                    String str = String.valueOf(value);
                    if (StringUtils.equals(enumStr, str)) {
                        return enumConstant;
                    }
                }
            }
        }
        return null;
    }
    
    @SuppressWarnings("unchecked")
    public static <T> T[] zcoGetEnumConstants(final Class<T> enumType) {
        if (enumType.isEnum()) {
            return enumType.getEnumConstants();
        } else {
            return (T[])enumConstantsTable.get(enumType).clone();
        }
    }
    
    /**
     * Convert enum to map
     * @param enumType
     * @return map<value, label>
     */
    public static Map<String, String> convertMap(final Class<? extends Enum<?>> enumType) {
        Map<String, String> map = new HashMap<>();
        PropertyDescriptor valueDesc = BeanUtils.getPropertyDescriptor(enumType, "value");
        PropertyDescriptor labelDesc = BeanUtils.getPropertyDescriptor(enumType, "label");
        Enum<?>[] enumConstants = enumType.getEnumConstants();
        for (Enum<?> enumConstant : enumConstants) {
            try {
                Object value = valueDesc.getReadMethod().invoke(enumConstant);
                Object label = labelDesc.getReadMethod().invoke(enumConstant);
                map.put(value.toString(), label.toString());
            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return map;
    }
}
