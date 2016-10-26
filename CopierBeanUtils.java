package vn.treelife.configuration;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import vn.treelife.util.DateUtils;

public final class CopierBeanUtils {

    public static void copy(final Object from, final Object to) {
        copyBean(from, to, false);
    }

    public static void copyExcludeNull(final Object from, final Object to) {
        copyBean(from, to, true);
    }

    private static void copyBean(final Object from, final Object to,
            final boolean excludeNull) {
        Map<String, Field> fromFields = analyze(from);
        Map<String, Field> toFields = analyze(to);
        fromFields.keySet().retainAll(toFields.keySet());
        for (Entry<String, Field> fromFieldEntry : fromFields.entrySet()) {
            final String name = fromFieldEntry.getKey();
            final Field sourceField = fromFieldEntry.getValue();
            final Field targetField = toFields.get(name);
            sourceField.setAccessible(true);
            if (Modifier.isFinal(targetField.getModifiers()))
                continue;
            targetField.setAccessible(true);
            try {
                if (excludeNull && sourceField.get(from) == null) {
                    continue;
                }
                if (targetField.getType().isAssignableFrom(
                        sourceField.getType())) {
                    targetField.set(to, sourceField.get(from));
                } else {
                    if (targetField.getType().isAssignableFrom(Integer.class)) {
                        targetField
                                .set(to, Integer.valueOf((String) sourceField
                                        .get(from)));
                    } else if (targetField.getType().isAssignableFrom(
                            Byte.class)) {
                        targetField.set(to,
                                Byte.valueOf((String) sourceField.get(from)));
                    } else if (targetField.getType().isAssignableFrom(
                            Date.class)) {
                        targetField.set(to, DateUtils
                                .stringToDate((String) sourceField.get(from)));
                    } else if (targetField.getType().isAssignableFrom(
                            Float.class)) {
                        targetField.set(to,
                                Float.valueOf((String) sourceField.get(from)));
                    } else if (targetField.getType().isAssignableFrom(
                            String.class)) {
                        if (sourceField.getType().isAssignableFrom(Date.class)) {
                            targetField
                                    .set(to, DateUtils
                                            .dateToString((Date) sourceField
                                                    .get(from)));
                        } else {
                            targetField.set(to,
                                    String.valueOf(sourceField.get(from)));
                        }
                    }
                }
            } catch (IllegalAccessException e) {
                throw new IllegalStateException("Can't access field!");
            }
        }
    }

    private static Map<String, Field> analyze(Object object) {
        if (object == null)
            throw new NullPointerException();

        Map<String, Field> map = new TreeMap<String, Field>();

        Class<?> current = object.getClass();
        if (current != Object.class) {
            for (Field field : getInheritedFields(current)) {
                if (!Modifier.isStatic(field.getModifiers())) {
                    if (!map.containsKey(field.getName())) {
                        map.put(field.getName(), field);
                    }
                }
            }
        }
        return map;
    }

    public static List<Field> getInheritedFields(Class<?> type) {
        List<Field> fields = new ArrayList<Field>();
        for (Class<?> c = type; c != null; c = c.getSuperclass()) {
            fields.addAll(Arrays.asList(c.getDeclaredFields()));
        }
        return fields;
    }
}
