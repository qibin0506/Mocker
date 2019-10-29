package org.loader.mocker;

import org.loader.mocker.annotation.Bool;
import org.loader.mocker.annotation.Lang;
import org.loader.mocker.annotation.Len;
import org.loader.mocker.annotation.NumberRange;
import org.loader.mocker.annotation.Time;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * How to use: <br />
 * <pre>
 * Data data = new Data();
 * data = Mocker.mock(data);
 * </pre>
 */
public class Mocker {

    public static <T> T mock(T obj) {
        try {
            Class clazz = obj.getClass();
            List<Field> fields = collectFields(clazz);
            for (Field f : fields) {
                assignValue(obj, f);
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }

        return obj;
    }

    private static <T> void assignValue(T obj, Field field) {
        try {
            field.setAccessible(true);
            Class type = field.getType();

            if (type == int.class || type == Integer.class) {
                field.set(obj, genInt(field));
            } else if (type == long.class || type == Long.class) {
                field.set(obj, genLong(field));
            } else if (type == float.class || type == Float.class) {
                field.set(obj, genFloat(field));
            } else if (type == double.class || type == Double.class) {
                field.set(obj, genDouble(field));
            } else if (type == String.class) {
                field.set(obj, genString(field));
            } else if (type == boolean.class || type == Boolean.class) {
                field.set(obj, genBoolean(field));
            } else if (type == List.class || type == ArrayList.class) {
                field.set(obj, genList(field));
            } else {
                Object object = mock(type.newInstance());
                field.set(obj, object);
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    private static List<Field> collectFields(Class clazz) {
        List<Field> fields = new ArrayList<>();

        Field[] fs = clazz.getDeclaredFields();
        if (fs != null && fs.length > 0) {
            fields.addAll(Arrays.asList(fs));
        }

        while (clazz != Object.class) {
            clazz = clazz.getSuperclass();
            fields.addAll(collectFields(clazz));
        }

        return fields;
    }

    private static int genInt(Field field) {
        return (int) randomNumber(field);
    }

    private static long genLong(Field field) {
        Time time = findAnnotation(field, Time.class);
        if (time != null) {
            return System.currentTimeMillis();
        } else {
            return (long) randomNumber(field);
        }
    }

    private static float genFloat(Field field) {
        return (float) randomNumber(field);
    }

    private static double genDouble(Field field) {
        return randomNumber(field);
    }

    private static String genString(Field field) {
        Time time = findAnnotation(field, Time.class);
        if (time != null) {
            return  new SimpleDateFormat(time.value()).format(System.currentTimeMillis());
        }

        NumberRange numberRange = findAnnotation(field, NumberRange.class);
        if (numberRange != null) {
            float from = numberRange.from();
            float to = numberRange.to();

            return String.valueOf(from + Math.random() * (to - from + 1));
        }

        String langValue = Lang.EN_US_LOWER;
        Lang lang = findAnnotation(field, Lang.class);
        if (lang != null) {
            langValue = lang.value();
            langValue = langValue.length() == 0 ? Lang.EN_US_LOWER : langValue;
        }

        int strLength = Len.DEFAULT_LEN;
        Len len = findAnnotation(field, Len.class);
        if (len != null) {
            strLength = len.value();
        }

        if (Lang.ZH_CN.equals(langValue)) {
            return rangeHan(strLength);
        } else if (Lang.EN_US_UPPER.equals(langValue)) {
            return rangeENUpper(strLength);
        }

        return rangeENLower(strLength);
    }

    private static boolean genBoolean(Field field) {
        boolean value = false;
        Bool bool = findAnnotation(field, Bool.class);
        if (bool != null) {
            value = bool.value();
        }

        return value;
    }

    private static List genList(Field field) {
        List result = new ArrayList();

        int listSize = Len.DEFAULT_LEN;
        Len len = findAnnotation(field, Len.class);
        if (len != null) {
            listSize = len.value();
        }

        Type genericType = field.getGenericType();
        if (genericType == null || !(genericType instanceof ParameterizedType)) {
            return result;
        }

        Class type = (Class) ((ParameterizedType) genericType).getActualTypeArguments()[0];

        Object value = null;
        for (int i = 0; i < listSize; i++) {
            if (type == int.class || type == Integer.class) {
                value = genInt(field);
            } else if (type == long.class || type == Long.class) {
                value = genLong(field);
            } else if (type == float.class || type == Float.class) {
                value = genFloat(field);
            }  else if (type == double.class || type == Double.class) {
                value = genDouble(field);
            } else if (type == String.class) {
                value = genString(field);
            } else if (type == Boolean.class) {
                value = genBoolean(field);
            } else {
                try {
                    value = mock(type.newInstance());
                } catch (Throwable t) {
                    t.printStackTrace();
                }
            }

            result.add(value);
        }

        return result;
    }

    private static String rangeHan(int len) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < len; i++) {
            builder.append((char) (0x4e00 + (int) (Math.random() * (0x9fa5 - 0x4e00 + 1))));
        }

        return builder.toString();
    }

    private static String rangeENLower(int len) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < len; i++) {
            builder.append((char) (97 + (int) (Math.random() * (122 - 97 + 1))));
        }

        return builder.toString();
    }

    private static String rangeENUpper(int len) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < len; i++) {
            builder.append((char) (65 + (int) (Math.random() * (90 - 65 + 1))));
        }

        return builder.toString();
    }

    private static double randomNumber(Field field) {
        float from = NumberRange.DEFAULT_FROM;
        float to = NumberRange.DEFAULT_TO;

        NumberRange numberRange = findAnnotation(field, NumberRange.class);
        if (numberRange != null) {
            from = numberRange.from();
            to = numberRange.to();
        }

        return from + Math.random() * (to - from + 1);
    }

    private static <A extends Annotation> A findAnnotation(Field field, Class<A> clazz) {
        if (!field.isAnnotationPresent(clazz)) {
            return null;
        }

        return field.getDeclaredAnnotation(clazz);
    }
}
