package com.iccues.movie.backend.utils;

import com.iccues.movie.backend.utils.data.Column;
import com.iccues.movie.backend.utils.data.DataEntity;
import com.iccues.movie.backend.utils.data.Table;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DataMapper {
    public static Object convertValue(Object value, Class<?> targetType) {
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
        if (Number.class.isAssignableFrom(targetType) && value instanceof Number) {
            Number number = (Number) value;
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

    public static<T> T mapRow(ResultSet resultSet, Class<T> clazz) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, SQLException {
        T obj = clazz.getDeclaredConstructor().newInstance();
        for(Field field : clazz.getDeclaredFields()) {
            Column column = field.getAnnotation(Column.class);
            String fieldName;
            if (column != null) fieldName = column.value();
            else fieldName = field.getName();

            Object fieldValue = resultSet.getObject(fieldName);

            if (fieldValue != null) {
                Object convertedValue = convertValue(fieldValue, field.getType());

                field.setAccessible(true);
                field.set(obj, convertedValue);
            }
        }

        return obj;
    }

    public static<T> List<T> select(Class<T> clazz) throws SQLException {
        String tableName = TableMapper.getTableName(clazz);

        List<T> results = new ArrayList<>();

        String sql = "SELECT * FROM " + tableName;

        try (
                Connection conn = DatabaseUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet resultSet = stmt.executeQuery()
        ) {
            while (resultSet.next()) {
                results.add(mapRow(resultSet, clazz));
            }
        } catch (InvocationTargetException | NoSuchMethodException |
                 InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        return results;
    }
}
