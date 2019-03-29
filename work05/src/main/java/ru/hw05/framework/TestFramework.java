package ru.hw05.framework;

import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.ClassPath;
import ru.hw05.annotations.After;
import ru.hw05.annotations.Before;
import ru.hw05.annotations.Test;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.annotation.IncompleteAnnotationException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class TestFramework {

    public static void run(String... args) throws IOException {
        for (String arg : args) {
            if (!arg.isEmpty()) {
                ImmutableSet<ClassPath.ClassInfo> classes = getAllClassesInPackage(arg);
                workWithTestClasses(classes);
            }

        }
    }

    private static void workWithTestClasses(ImmutableSet<ClassPath.ClassInfo> classes) {
        for (ClassPath.ClassInfo classInfo : classes) {
            workWithClass(classInfo.getName());
        }
    }

    private static void workWithClass(String className) {
        try {
            Class cl = Class.forName(className);
            workWithClassMethods(cl);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    private static void workWithClassMethods(Class<?> cl) {
        Method[] method = cl.getDeclaredMethods();
        Class<? extends Annotation>[] cls = new Class[]{After.class, Before.class};
        Optional<Method> after = getAnnotatedMethod(method, After.class);
        Optional<Method> before = getAnnotatedMethod(method, Before.class);

        setAccessible(after);
        setAccessible(before);

        for (Method md : method) {

            Object object = null;
            try {
                object = cl.newInstance();
                md.setAccessible(true);

                if (md.isAnnotationPresent(Test.class) && otherTestAnnotationsNotPresent(md, cls)) {
                    if (before.isPresent()) {
                        invokePresent(before, object);
                    }
                    md.invoke(object);
                    if (after.isPresent()) {
                        invokePresent(after, object);
                    }
                }
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }

        }
    }

    private static void invokePresent(Optional<Method> method, Object object) throws IllegalAccessException, InvocationTargetException {
        method.get().invoke(object);
    }
    
    private static void setAccessible(Optional<Method> method) {
        if (method.isPresent() && !method.get().isAccessible())
            method.get().setAccessible(true);
    }

    private static Optional<Method> getAnnotatedMethod(Method[] methods, Class<? extends Annotation> annotation) {
        try (Stream<Method> method = Arrays.stream(methods).filter(item -> item.isAnnotationPresent(annotation))) {
            Collection<Method> collection = method.collect(Collectors.toList());
            if (collection.size() > 1)
                throw new IncompleteAnnotationException(annotation, annotation.getName() + " must be only one per class.");
            return collection.size() == 0 ? Optional.empty() : Optional.of(((List<Method>) collection).get(0));
        }
    }

    private static boolean otherTestAnnotationsNotPresent(Method md, Class<? extends Annotation>[] cls) {
        try (Stream<Class<? extends Annotation>> annotations = Arrays.stream(cls).filter(item -> md.isAnnotationPresent(item))) {
            if (annotations.count() > 0) return false;
        }
        return true;
    }

    private static ImmutableSet<ClassPath.ClassInfo> getAllClassesInPackage(String packageName) throws IOException {
        ClassPath classPath = ClassPath.from(ClassLoader.getSystemClassLoader());
        ImmutableSet<ClassPath.ClassInfo> classesList = classPath.getTopLevelClasses(packageName);
        return classesList;

    }

}
