package ru.hw05.framework;

import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.ClassPath;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.hw05.annotations.After;
import ru.hw05.annotations.Before;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class TestFrameworkTest {

    private String testPackageName;
    private String testClassName;
    private Class cl;
    private Object testObject;
    private String testikClassName;
    private Class clTestik;
    private Object testikObject;

    @BeforeEach
    void setUp() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        testPackageName = "ru.hw05.testik";
        testClassName = "ru.hw05.framework.TestFramework";
        testikClassName = "ru.hw05.testik.Testik";
        cl = Class.forName(testClassName);
        clTestik = Class.forName(testikClassName);
        testObject = cl.newInstance();
        testikObject = clTestik.newInstance();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void run() {
    }

    @Test
    void ImmutableSetClassesList() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Optional<Method> method = setMethod(cl, "getAllClassesInPackage", new Class[]{String.class});
        ImmutableSet<ClassPath.ClassInfo> classInfos = (ImmutableSet<ClassPath.ClassInfo>) method.get().invoke(testObject, testPackageName);
        assertEquals(classInfos.toString(), "[" + testikClassName + "]");
    }


    @Test
    void otherTestAnnotationNotPresent() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        Optional<Method> method = setMethod(cl, "otherTestAnnotationsNotPresent", new Class[]{Method.class, Class[].class});
        Optional<Method> method1 = setMethod(clTestik, "firstTest", new Class[]{});

        Class<? extends Annotation>[] cls = new Class[]{After.class, Before.class};
        assertTrue((boolean) method.get().invoke(testObject, method1.get(), cls));

        Optional<Method> method2 = setMethod(clTestik, "setUp", new Class[]{});
        assertFalse((boolean) method.get().invoke(testObject, method2.get(), cls));
    }

    @Test
    void getAnnotatedMethod() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method[] methods = clTestik.getDeclaredMethods();
        Optional<Method> method = setMethod(cl, "getAnnotatedMethod", new Class[]{Method[].class, Class.class});
        Optional<Method> before = (Optional<Method>) method.get().invoke(testObject, methods, Before.class);
        assertTrue(before.isPresent());
        assertEquals(before.get().getName(), "setUp");
    }

    @Test
    void getAnnotatedMethodExceptinon() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {


        Method[] methods = clTestik.getDeclaredMethods();
        Optional<Method> method = setMethod(cl, "getAnnotatedMethod", new Class[]{Method[].class, Class.class});
        Throwable throwable = assertThrows(InvocationTargetException.class, () -> method.get().invoke(testObject, methods, After.class));
        assertEquals("ru.hw05.annotations.After missing element ru.hw05.annotations.After must be only one per class.",
                throwable.getCause().getMessage());
    }

    private Optional<Method> setMethod(Class cl, String methodName, Class[] paramTypes) throws NoSuchMethodException {
        Optional<Method> method = Optional.ofNullable(cl.getDeclaredMethod(methodName, paramTypes));
        assertTrue(method.isPresent());
        method.get().setAccessible(true);
        assertTrue(method.get().isAccessible());
        return method;
    }

}