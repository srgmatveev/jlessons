package ru.hw08.json.json;

import javax.json.Json;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.AbstractCollection;
import java.util.Collections;
import java.util.Map;

public class JSONSerializerImp implements JSONSerializer {
    JsonBuilderFactory factory = Json.createBuilderFactory(null);

    @Override
    public String toString() {
        return "JSONSerializerImp{}";
    }

    @Override
    public String toJSON(final Object object) {
        if (object == null) return null;
        if (isAbstractCollection(object)) return JSONFromCollection(object);
        if (isObject(object)) return JSONFromObject(object);
        return "";
    }


    private boolean isAbstractCollection(Object object) {
        if (object == null) return false;
        Class<?> cls = object.getClass();
        return AbstractCollection.class.isAssignableFrom(cls);
    }

    private boolean isObject(Object object) {
        if (object == null) return false;
        Class<?> cl = object.getClass();
        return Object.class.isAssignableFrom(cl);
    }

    private String JSONFromObject(Object object) {
        if (object == null) return null;
        Class<?> cls = object.getClass();
        JsonObjectBuilder jsonObjectBuilder = factory.createObjectBuilder();
        while (cls != null) {
            Field[] fields = cls.getDeclaredFields();
            AccessibleObject.setAccessible(fields, true);
            for (Field field : fields) {

                if (!Modifier.isStatic(field.getModifiers()) || !Modifier.isTransient(field.getModifiers())) {
                    try {
                        Class<?> type = field.getType();
                        Object value = field.get(object);
                        System.out.println(type.getName());
                        switch (type.getName()) {
                            case "int":
                                jsonObjectBuilder.add(field.getName(), (int) value);
                                break;
                            case "java.lang.Integer":
                                jsonObjectBuilder.add(field.getName(), (int) value);
                                break;
                            case "double":
                                jsonObjectBuilder.add(field.getName(), (double) value);
                                break;
                            case "java.lang.Double":
                                jsonObjectBuilder.add(field.getName(), (double) value);
                                break;
                            default:
                                jsonObjectBuilder.add(field.getName(), toJSON(value));
                                break;
                        }

                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }


                }

            }
            cls = cls.getSuperclass();

        }
        ;
        return jsonObjectBuilder.build().toString();
    }

    private String JSONFromCollection(Object object) {
        return "hello";
    }

}
