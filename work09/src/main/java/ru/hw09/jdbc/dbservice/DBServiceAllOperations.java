package ru.hw09.jdbc.dbservice;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.lang3.StringUtils;
import ru.hw09.jdbc.connection.ConnectionHelper;
import ru.hw09.jdbc.data.DataField;
import ru.hw09.jdbc.data.DataSet;
import ru.hw09.jdbc.executor.DDLExecutor;
import ru.hw09.jdbc.executor.Executor;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class DBServiceAllOperations extends DBServiceDropCreateTables {

    public DBServiceAllOperations(ConnectionHelper connectionHelper) {
        super(connectionHelper);
    }



    @Override
    public <T extends DataSet> void save(T data) throws SQLException {
        Executor executor = new DDLExecutor(super.getConnection());
        String tableName = data.getTableName();
        StringBuilder sb = new StringBuilder(String.format("INSERT INTO %s ", tableName));
        sb.append(getFieldsString(data));
        System.out.println(sb.toString());
        executor.execUpdate(sb.toString());
    }


    private <T extends DataSet> String getFieldsString(T data) {
        Map<String, String> map = getFieldsMap(data);
        StringBuilder keys = new StringBuilder()
                                .append("(");
        StringBuilder values = new StringBuilder()
                                .append("VALUES(");
        for(Map.Entry<String, String> item :map.entrySet()){
            keys.append(addKV(item.getKey()));
            values.append(addKV(item.getValue()));
        }
        keys.append(")");
        values.append(")");

        String k = keys.toString().replaceFirst(" ,","");
        String v = values.toString().replaceFirst(" ,","");
        return new StringBuilder(k).append(" ").append(v).toString();
    }

    private static String addKV(String str){

        return new StringBuilder(" , ").append(str).toString();
    }

    private static <T extends DataSet> Map<String, String> getFieldsMap(T data){
        Field[] fields = data.getClass().getDeclaredFields();
        AccessibleObject.setAccessible(fields, true);
        Map<String, String> map = new HashMap<>();
        if (data.getId()!=-1){
            map.put("id", String.valueOf(data.getId()));
        }

        for (Field field : fields) {
            if (field.isAnnotationPresent(DataField.class)) {
                String name = StringUtils.capitalize(field.getName());
                String funcName = getFunctionName("get", name);
                Method method = getMethod(data, funcName);
                String tmpVal = getParamValue(data, method);
                if (tmpVal != null)
                    map.put(field.getName(), tmpVal);
            }
        }
        return map;
    }
    private static <T extends DataSet> String getParamValue(T data, Method method) {
        try {
            if (method.getReturnType().toString().equals("class java.lang.String"))
                return new StringBuilder("\"")
                        .append(method.invoke(data).toString())
                        .append("\"")
                        .toString();
            else
                return method.invoke(data).toString();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String getFunctionName(String prefix, String str) {
        return new StringBuilder().append(prefix).append(str).toString();
    }

    private static <T extends DataSet> Method getMethod(T data, String methName) {
        Method method = null;
        try {
            method = data.getClass().getDeclaredMethod(methName);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        method.setAccessible(true);
        return method;
    }

    @Override
    public <T extends DataSet> T load(long id, Class<T> clazz) {
        return super.load(id, clazz);
    }

    @Override
    public <T extends DataSet> Collection<T> loadAll() {
        return super.loadAll();
    }
}
