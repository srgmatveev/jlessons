package ru.hw10.jdbc.utils;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.ClassPath;
import com.google.common.reflect.ClassPath.*;
import ru.hw10.jdbc.data.DataSet;
import ru.hw10.jdbc.data.IsOneToOne;

public class TableUtils {
    static public Optional<String> getTableNameFromFK(String string, String regexp) {
        Pattern pattern = Pattern.compile(regexp, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(string);
        boolean found = matcher.find();
        if (found)
            return Optional.of(matcher.group(1));
        else
            return Optional.empty();


    }

    static public String getId(String item) {
        Optional<String> optional = getTableNameFromFK(item, "\\{id='(\\w*)'");
        return optional.isPresent() ? optional.get() : "";
    }

    ;

    static Optional<String> getTopPackage(String str) {
        Optional<String> pack;
        String[] strings = str.split("\\.");
        List<String> array = Arrays.asList(strings);

        if (array.size() > 0) {
            switch (array.size()) {
                case 1:
                    return Optional.of(array.get(0));
                default:
                    return Optional.of(array.get(0) + "." + array.get(1));
            }

        } else
            return Optional.empty();
    }

    static public Optional<Set<String>> getAddonsCreateTables(String table) {
        ClassPath classPath = null;
        try {
            classPath = ClassPath.from(ClassLoader.getSystemClassLoader());
        } catch (IOException e) {
            e.printStackTrace();
            return Optional.empty();
        }
        Optional<String> packageName = getTopPackage(TableUtils.class.getPackageName());

        Set<String> set = new TreeSet<>();

        if (packageName.isPresent()) {
            ImmutableSet<ClassInfo> classesList = classPath.getTopLevelClassesRecursive(packageName.get());
            for (ClassInfo info : classesList.asList()) {
                Class<?> load = info.load();
                if (isDataSetExtends(load))
                    for (String item : tablesNames(load)) {
                        set.add(item);
                    }

            }

            return Optional.of(set);
        }


        return Optional.empty();
    }

    static private List<String> tablesNames(Class<?> cls) {
        List<String> list = new ArrayList<>();
        Field[] fields = cls.getDeclaredFields();
        AccessibleObject.setAccessible(fields, true);
        for (Field field : fields) {
            if (field.isAnnotationPresent(IsOneToOne.class)) {
                IsOneToOne annotation = field.getAnnotation(IsOneToOne.class);
                if (!annotation.name().isEmpty())
                    list.add(annotation.name());
                else
                    list.add(field.getType().getSimpleName());

            }
        }

        return list;
    }

    static private boolean isDataSetExtends(Class<?> cls) {
        if (cls == null) return false;
        if (cls.getName().equals("java.lang.Object")) return false;
        Pattern pattern = Pattern.compile("\\.DataSet$");
        Matcher matcher = pattern.matcher(cls.getName());
        boolean found = matcher.find();
        if (found)
            return false;
        while (true) {
            cls = cls.getSuperclass();
            if (cls == null) break;
            matcher = pattern.matcher(cls.getName());
            found = matcher.find();
            if (found)
                return true;
        }
        return false;
    }

    public static <T extends DataSet> T returnObject(String str) {
        try {
            Class<?> aClass = Class.forName(str);
            if (isDataSetExtends(aClass)) {

                Constructor<?> constructor = aClass.getConstructors()[0];
                Collection<Object> objects = getConstructorParams(constructor);
                try {
                    return (T) constructor.newInstance(objects.toArray());
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static <T extends DataSet> T generateNewInstance(Class<T> clazz) {
        Constructor<T>[] constructors = (Constructor<T>[]) clazz.getConstructors();
        Constructor<T> constructor = constructors[0];
        Collection<Object> objects = TableUtils.getConstructorParams(constructor);
        try {
            return constructor.newInstance(objects.toArray());
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();

        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }


    private static Collection<Object> getConstructorParams(Constructor constructor) {
        Collection<Object> objects = new ArrayList<>();
        Parameter[] parameters = constructor.getParameters();
        for (Parameter param : parameters) {
            switch (param.getType().getName()) {
                case "java.lang.String":
                    objects.add(new String(""));
                    break;
                case "boolean":
                case "java.lang.Boolean":
                    objects.add(true);
                case "short":
                case "byte":
                case "int":
                case "java.lang.Integer":
                case "long":
                case "java.lang.Long":
                    objects.add(0);
                    break;
                case "float":
                case "java.lang.Float":
                case "java.lang.Double":
                case "double":
                    objects.add(0.0);
                    break;
                default:
                    if (TableUtils.isDataSetExtends(returnObject(param.getType().getName()).getClass()))
                       //System.out.println(param.getType().getName());
                        objects.add(returnObject(param.getType().getName()));
                    else
                        objects.add(new Object());
                    break;
            }
        }
        return objects;
    }

}
