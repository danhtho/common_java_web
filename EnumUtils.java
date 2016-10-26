/** 
 * EnumUtils.java         2016/10/17
 *
 * Copyright (c) 2016 Transcosmos Technologies Vietnam Co., Ltd.
 * Unit 8, 8th Floor, E.Town 3, 364 Cong Hoa St., Ward 13, Tan Binh Dist.,
 * Ho Chi Minh City, Viet Nam.
 * All rights reserved.
 *
 * Phan mem nay la thong tin mat (TCT-VN Confidential) cua Transcosmos Technologies Vietnam Co., Ltd.
 * Ban se khong duoc phep tiet lo thong tin nay va chi duoc su dung khi duoc su dong y cua Trancosmos
 * Technologies Vietnam Co., Ltd.
 */

package vn.treelife.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.BeanUtils;

/**
 * Lop xu ly cho EnumUtils
 * 
 * 2016/10/17
 */
public class EnumUtils {
    private static Map<Class<?>, Object[]> enumConstantsTable = new HashMap<Class<?>, Object[]>();

    public static <T extends InterfaceEnum> T valueOf(final Class<T> enumType,
            final Object value) {
        int intValue;
        if (value instanceof Number) {
            intValue = ((Number) value).intValue();
        } else if (value instanceof String) {
            intValue = NumberUtils.toInt((String) value);
            if (!StringUtils.equals((String) value, Integer.toString(intValue))) {
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
    public static <T> T[] zcoGetEnumConstants(final Class<T> enumType) {
        if (enumType.isEnum()) {
            return enumType.getEnumConstants();
        } else {
            return (T[]) enumConstantsTable.get(enumType).clone();
        }
    }

    /**
     * <h1>convertMap</h1>
     * <p>
     * Convert enum to map
     * </p>
     * <h3>input</h3>
     * <table style="border: 1px solid black;">
     * <tr>
     * <th style="border: 1px solid black;">param</th>
     * <th style="border: 1px solid black;">type</th>
     * <th style="border: 1px solid black;">descripton</th>
     * </tr>
     * <tr>
     * <td style="border: 1px solid black;">enumType</td>
     * <td style="border: 1px solid black;">Class<? extends Enum<?>></td>
     * <td style="border: 1px solid black;">enumType</td>
     * </tr>
     * </table>
     * 
     * @return Map<String, String> convertMap
     */
    public static Map<String, String> convertMap(
            final Class<? extends Enum<?>> enumType) {
        Map<String, String> map = new HashMap<>();
        PropertyDescriptor valueDesc = BeanUtils.getPropertyDescriptor(
                enumType, "value");
        PropertyDescriptor labelDesc = BeanUtils.getPropertyDescriptor(
                enumType, "label");
        Enum<?>[] enumConstants = enumType.getEnumConstants();
        for (Enum<?> enumConstant : enumConstants) {
            try {
                Object value = valueDesc.getReadMethod().invoke(enumConstant);
                Object label = labelDesc.getReadMethod().invoke(enumConstant);
                map.put(value.toString(), label.toString());
            } catch (IllegalAccessException | IllegalArgumentException
                    | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return map;
    }

    public static String getLabel(final Class<? extends Enum<?>> enumType,
            final Object objectValue) {
        String labelReturn = "";
        PropertyDescriptor valueDesc = BeanUtils.getPropertyDescriptor(
                enumType, "value");
        PropertyDescriptor labelDesc = BeanUtils.getPropertyDescriptor(
                enumType, "label");
        Enum<?>[] enumConstants = enumType.getEnumConstants();
        for (Enum<?> enumConstant : enumConstants) {
            try {
                Object value = valueDesc.getReadMethod().invoke(enumConstant);
                Object label = labelDesc.getReadMethod().invoke(enumConstant);
                if (objectValue instanceof Integer) {
                    if (value == objectValue) {
                        return label.toString();
                    }
                } else if (objectValue != null) {
                    if (value.equals(((Byte) objectValue).intValue())) {
                        return label.toString();
                    }
                }
            } catch (IllegalAccessException | IllegalArgumentException
                    | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return labelReturn;
    }
}
