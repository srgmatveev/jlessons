package ru.hw10.jdbc.dbservice;

import java.lang.reflect.*;

import org.apache.commons.lang3.StringUtils;
import ru.hw10.jdbc.connection.ConnectionHelper;
import ru.hw10.jdbc.data.DataField;
import ru.hw10.jdbc.data.DataSet;
import ru.hw10.jdbc.data.IsManyToOne;
import ru.hw10.jdbc.data.IsOneToOne;
import ru.hw10.jdbc.executor.DDLExecutor;
import ru.hw10.jdbc.executor.DMLExecutor;
import ru.hw10.jdbc.executor.Executor;
import ru.hw10.jdbc.handler.ResultHandler;
import ru.hw10.jdbc.utils.TableUtils;

import javax.naming.spi.DirStateFactory;
import javax.sql.rowset.CachedRowSet;
import javax.xml.transform.Result;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class DBServiceAllOperations extends DBServiceDropCreateTables {
    private static final String SELECT_TABLE = "select * from %s where id=%s";
    private static String tableNameVal = "";

    public DBServiceAllOperations(ConnectionHelper connectionHelper) {
        super(connectionHelper);
    }


    @Override
    public <T extends DataSet> void save(T data) throws SQLException {
        try {


            Executor executor = new DDLExecutor(super.getConnection());
            String tableName = data.getTableName();
            StringBuilder sb = new StringBuilder(String.format("INSERT INTO %s ", tableName));
            sb.append(getFieldsString(data));
            System.out.println(sb.toString());
            setId(data, executor.execUpdate(sb.toString()));
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println(String.format("==== Table '%s': SAVE ERROR \"%s\" ====", data.getTableName(), e.getMessage()));
        }

    }

    private <T extends DataSet> void setId(T data, Map<Integer, ResultSet> map) throws SQLException {
        if (map.size() > 0 && data.getId() == -1) {
            long id = -1;
            for (Map.Entry<Integer, ResultSet> entry : map.entrySet()) {
                ResultSet res = entry.getValue();
                if (res.next()) id = res.getLong(1);
                break;
            }

            setIdFromLong(data, id);
        }
    }

    private <T extends DataSet> void setIdFromLong(T data, long id) {

        try {
            Method method = DataSet.class.getDeclaredMethod("setId", long.class);
            method.setAccessible(true);
            try {
                if (id != -1)
                    method.invoke(data, id);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    private <T extends DataSet> String getFieldsString(T data) {
        String openingParenthesis = "(";
        String closeParenthesis = ")";

        Map<String, String> map = getFieldsMap(data);
        StringBuilder keys = new StringBuilder()
                .append(openingParenthesis);
        StringBuilder values = new StringBuilder()
                .append("VALUES(");
        for (Map.Entry<String, String> item : map.entrySet()) {
            keys.append(addKV(item.getKey()));
            if (fromDataSet(data, item))
                values.append(addKV(TableUtils.getId(item.getValue())));
            else
                values.append(addKV(item.getValue()));
        }
        keys.append(closeParenthesis);
        values.append(closeParenthesis);

        String k = keys.toString().replaceFirst(" ,", "");
        String v = values.toString().replaceFirst(" ,", "");
        return new StringBuilder(k).append(" ").append(v).toString();
    }


    private static <T extends DataSet> boolean fromDataSet(T data, Map.Entry<String, String> item) {
        try {
            if (data.getClass().getDeclaredField(item.getKey()).isAnnotationPresent(IsOneToOne.class)
                    || data.getClass().getDeclaredField(item.getKey()).isAnnotationPresent(IsManyToOne.class))
                return true;
        } catch (NoSuchFieldException e) {
            return false;
        }
        return false;
    }

    private static String addKV(String str) {

        return new StringBuilder(" , ").append(str).toString();
    }

    private static <T extends DataSet> Map<String, String> getFieldsMap(T data) {
        Field[] fields = data.getClass().getDeclaredFields();
        AccessibleObject.setAccessible(fields, true);
        Map<String, String> map = new HashMap<>();
        if (data.getId() != -1) {
            map.put("id", String.valueOf(data.getId()));
        }

        for (Field field : fields) {
            if (field.isAnnotationPresent(DataField.class)) {
                String name = StringUtils.capitalize(field.getName());
                String funcName = getFunctionName("get", name);
                Method method = getMethod(data, funcName, null);
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

    private static <T extends DataSet> Method getMethod(T data, String methName, Class[] cls) {
        Method method = null;
        try {
            method = data.getClass().getDeclaredMethod(methName, cls);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        method.setAccessible(true);
        return method;
    }

    @Override
    public <T extends DataSet> T load(long id, Class<T> clazz) throws SQLException {
        Executor executor = new DMLExecutor(super.getConnection());
        String table = getTableName(clazz);
        if (table == null) return null;
        CachedRowSet res = executor.execQuery(String.format(SELECT_TABLE, table, id), new ResultHandlerGetName());
        return returnLoadNewInstance(clazz, res);
    }

    private <T extends DataSet> T returnLoadNewInstance(Class<T> clazz, CachedRowSet set) throws SQLException {
        T t = TableUtils.generateNewInstance(clazz);
        set.next();
        long id = set.getLong("id");
        setIdFromLong(t, id);
        fillTByResultSet(t, set);
        System.out.println(t);
        set.close();
        return t;
    }


    private <T extends DataSet> void fillTByResultSet(T data, CachedRowSet set) {

        Field[] fields = data.getClass().getDeclaredFields();
        AccessibleObject.setAccessible(fields, true);

        for (Field field : fields) {
            if (field.isAnnotationPresent(DataField.class)) {
                String name = StringUtils.capitalize(field.getName());
                String funcName = getFunctionName("set", name);
                Class[] cls = new Class[]{field.getType()};
                Method method = getMethod(data, funcName, cls);

                try {
                    switch (field.getType().getTypeName()) {
                        case "java.lang.String":
                            method.invoke(data, set.getString(field.getName()));
                            break;
                        case "int":
                        case "java.lang.Integer":
                            method.invoke(data, set.getInt(field.getName()));
                            break;
                        case "boolean":
                        case "java.lang.Boolean":
                            method.invoke(data, set.getBoolean(field.getName()));
                            break;
                        default:
                            System.out.println(field.getType().getTypeName());
                            break;
                    }


                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }


        }
    }


    private <T extends DataSet> String getTableName(Class<T> clazz) {

        T t = TableUtils.generateNewInstance(clazz);
        setStaticTableNameVal(t);
        return t.getTableName();

    }


    private <T extends DataSet> void setStaticTableNameVal(T data) {
        tableNameVal = data.getTableName();
    }




    @Override
    public <T extends DataSet> Collection<T> loadAll() {
        return super.loadAll();
    }


    private static class ResultHandlerGetName implements ResultHandler {
        public void handle(ResultSet result) throws SQLException {
            System.out.println("Read table '" + DBServiceAllOperations.tableNameVal + "':");
            ResultSetMetaData rsmd = result.getMetaData();
            int n = rsmd.getColumnCount();
            for (int i = 1; i <= n; ++i) {
                System.out.print(rsmd.getColumnLabel(i));
                System.out.print("|");
            }
            System.out.println();
            result.next();
            for (int i = 1; i <= n; ++i) {
                System.out.print(result.getString(i));
                System.out.print("|");
            }
            System.out.println();

        }
    }

}
