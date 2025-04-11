package com.iccues.movie.backend.utils;

import com.iccues.movie.backend.utils.data.Generated;
import com.iccues.movie.backend.utils.data.TableInfo;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataMapper {
    private static Object convertValue(Object value, Class<?> targetType) {
        Class<?> valueType = value.getClass();

        if (targetType.isAssignableFrom(valueType)) return value;

        if (targetType.isPrimitive()) {
            if (targetType == int.class) targetType = Integer.class;
            else if (targetType == long.class) targetType = Long.class;
            else if (targetType == double.class) targetType = Double.class;
            else if (targetType == float.class) targetType = Float.class;
            else if (targetType == boolean.class) targetType = Boolean.class;
            else if (targetType == char.class) targetType = Character.class;
            else if (targetType == byte.class) targetType = Byte.class;
            else if (targetType == short.class) targetType = Short.class;
        }

        // Handle number conversions
        if (Number.class.isAssignableFrom(targetType) && value instanceof Number number) {
            if (targetType == Integer.class) return number.intValue();
            if (targetType == Long.class) return number.longValue();
            if (targetType == Double.class) return number.doubleValue();
            if (targetType == Float.class) return number.floatValue();
            if (targetType == Short.class) return number.shortValue();
            if (targetType == Byte.class) return number.byteValue();
        }

        // Handle Timestamp to LocalDateTime
        if (value instanceof java.sql.Timestamp && targetType == java.time.LocalDateTime.class) {
            return ((java.sql.Timestamp) value).toLocalDateTime();
        }

        // Handle Date to LocalDate
        if (value instanceof java.sql.Date && targetType == java.time.LocalDate.class) {
            return ((java.sql.Date) value).toLocalDate();
        }

        // Handle Boolean conversion from numeric
        if (targetType == Boolean.class && value instanceof Number) {
            return ((Number) value).intValue() != 0;
        }

        // Fallback
        throw new RuntimeException("Cannot convert " + value.getClass().getName() + " to " + targetType.getName());
    }

    private static<T> T mapRow(ResultSet resultSet, Class<T> clazz) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, SQLException {
        T obj = clazz.getDeclaredConstructor().newInstance();
        for(Field field : clazz.getDeclaredFields()) {
            String fieldName = TableInfo.getColumnName(field);

            Object fieldValue = resultSet.getObject(fieldName);

            if (fieldValue != null) {
                Object convertedValue = convertValue(fieldValue, field.getType());

                field.setAccessible(true);
                field.set(obj, convertedValue);
            }
        }

        return obj;
    }

    private static ResultSet getSelectResultSet(String tableName, String whereTemplate, Object... params) throws SQLException {
        String sql = "SELECT * FROM " + tableName;
        if(whereTemplate != null && !whereTemplate.isBlank()) sql += " WHERE " + whereTemplate;

        Connection conn = DatabaseUtil.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);

        for(int i = 0; i < params.length; i++) {
            stmt.setObject(i + 1, params[i]);
        }

        return stmt.executeQuery();
    }

    public static<T> List<T> selectAll(Class<T> clazz, String whereTemplate, Object... params) {
        String tableName = TableInfo.getTableName(clazz);

        List<T> results = new ArrayList<>();

        try (
                ResultSet resultSet = getSelectResultSet(tableName, whereTemplate, params);
        ) {
            while (resultSet.next()) {
                results.add(mapRow(resultSet, clazz));
            }
        } catch (Exception e) {
            return null;
        }

        return results;
    }

    public static<T> List<T> selectAll(Class<T> clazz) {
        String tableName = TableInfo.getTableName(clazz);

        List<T> results = new ArrayList<>();

        try (
                ResultSet resultSet = getSelectResultSet(tableName, null)
        ) {
            while (resultSet.next()) {
                results.add(mapRow(resultSet, clazz));
            }
        } catch (Exception e) {
            return null;
        }

        return results;
    }

    public static<T> T selectFirst(Class<T> clazz, String whereTemplate, Object... params) {
        String tableName = TableInfo.getTableName(clazz);

        try (
                ResultSet resultSet = getSelectResultSet(tableName, whereTemplate, params)
        ) {
            if(resultSet.next()) {
                return mapRow(resultSet, clazz);
            }
        } catch (Exception ignore) {}
        return null;
    }

    public static<T> T selectFirst(Class<T> clazz) {
        String tableName = TableInfo.getTableName(clazz);

        try (
                ResultSet resultSet = getSelectResultSet(tableName, null)
        ) {
            if(resultSet.next()) {
                return mapRow(resultSet, clazz);
            }
        } catch (Exception ignore) {}
        return null;
    }

    public static void insert(Object object) throws IllegalAccessException, SQLException {
        Class<?> clazz = object.getClass();
        String tableName = TableInfo.getTableName(clazz);

        List<String> columns = new ArrayList<>();
        List<String> placeholders = new ArrayList<>();
        List<Object> values = new ArrayList<>();

        List<String> generatedColumns = new ArrayList<>();

        for (Field field : clazz.getDeclaredFields()) {
            String fieldName = TableInfo.getColumnName(field);

            if(field.isAnnotationPresent(Generated.class)) {
                generatedColumns.add(fieldName);
            } else {
                columns.add(fieldName);
                placeholders.add("?");

                field.setAccessible(true);
                values.add(field.get(object));
            }
        }

        String sql = String.format("INSERT INTO %s (%s) VALUES(%s)",
                tableName,
                String.join(",", columns),
                String.join(",", placeholders)
                );

        try (PreparedStatement stmt = DatabaseUtil.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            for (int i = 0; i < values.size(); i++) {
                stmt.setObject(i + 1, values.get(i));
            }
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    for (int i = 0; i < generatedColumns.size(); i++) {
                        Field field = clazz.getDeclaredField(generatedColumns.get(i));
                        field.setAccessible(true);

                        Object convertedValue = convertValue(generatedKeys.getObject(i + 1), field.getType());
                        field.set(object, convertedValue);
                    }
                }
            } catch (NoSuchFieldException e) {
                throw new RuntimeException(e);
            }

            // 此处可能存在bug：
            // 在Generated注解大于1个，且其顺序与MySQL表返回的顺序不同时，
            // 回填会错误
        }
    }

//    public static void update(Object object) throws IllegalAccessException, SQLException {
//        Class<?> clazz = object.getClass();
//        String tableName = TableInfo.getTableName(clazz);
//    }
}
