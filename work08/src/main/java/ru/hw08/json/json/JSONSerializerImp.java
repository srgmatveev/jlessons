package ru.hw08.json.json;

import javax.json.*;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

public class JSONSerializerImp implements JSONSerializer {
    JsonBuilderFactory factory;

    public JSONSerializerImp() {
        factory = Json.createBuilderFactory(null);
    }

    @Override
    public String toString() {
        return "JSONSerializerImp{}";
    }

    @Override
    public String toJSON(final Object object) {
        if (object == null) return null;
        if (isAbstractCollection(object)) return JSONFromCollection(object);
        if (isArray(object)) return JSONFromArray(object);
        if (isObject(object)) return JSONFromObject(object);
        return "";
    }


    private boolean isAbstractCollection(Object object) {
        if (object == null) return false;
        Class<?> cls = object.getClass();
        return AbstractCollection.class.isAssignableFrom(cls);
    }

    private boolean isArray(Object object) {
        if (object == null) return false;
        Class<?> cls = object.getClass();
        return cls.isArray();
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
                       // System.out.println(type.getName());
                        switch (type.getName()) {
                            case "int":
                            case "java.lang.Integer":
                                if (value == null) value = 0;
                                jsonObjectBuilder.add(field.getName(), (int) value);
                                break;
                            case "double":
                            case "java.lang.Double":
                                if (value == null) value = 0.0;
                                jsonObjectBuilder.add(field.getName(), (double) value);
                                break;
                            case "java.lang.String":
                                if (value == null) value = "";
                                jsonObjectBuilder.add(field.getName(), (String) value);
                                break;
                            case "boolean":
                            case "java.lang.Boolean":
                                if (value == null) value = false;
                                jsonObjectBuilder.add(field.getName(), (Boolean) value);
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
        return replaceCharacters(jsonObjectBuilder.build());
    }

    private String JSONFromCollection(Object object) {
        if (object == null) return null;
        Collection collection = (Collection) object;
        return toJSON(collection.toArray());

    }

    private String JSONFromArray(Object object) {
        if (object == null) return null;
        JsonArrayBuilder jsonArrayBuilder = factory.createArrayBuilder();
        int length = Array.getLength(object);
        for (int i = 0; i < length; ++i) {
            Object value = Array.get(object, i);
            switch (value.getClass().getName()) {
                case "int":
                case "java.lang.Integer":
                    if (value == null) value = 0;
                    jsonArrayBuilder.add((int) value);
                    break;
                case "double":
                case "java.lang.Double":
                    if (value == null) value = 0.0;
                    jsonArrayBuilder.add((double) value);
                    break;
                case "java.lang.String":
                    if (value == null) value = "";
                    jsonArrayBuilder.add((String) value);
                    break;
                case "boolean":
                case "java.lang.Boolean":
                    if (value == null) value = false;
                    jsonArrayBuilder.add((Boolean) value);
                    break;
                default:
                    if (value == null) jsonArrayBuilder.addNull();
                    else
                        jsonArrayBuilder.add(toJSON(value));
                    break;
            }

        }
        return replaceCharacters(jsonArrayBuilder.build());
    }

    private String replaceCharacters(final JsonValue value)
    {
        return value.toString()
                .replace("\"{", "{")
                .replace("}\"", "}")
                .replace("\\\"", "\"")
                .replace("\"[", "[")
                .replace("]\"", "]");
    }

}
