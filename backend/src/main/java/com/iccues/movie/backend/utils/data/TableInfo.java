package com.iccues.movie.backend.utils.data;

import java.lang.reflect.Field;

public class TableInfo {
    public static String getTableName(Class<?> clazz) {
        if(!clazz.isAnnotationPresent(DataEntity.class)) throw new RuntimeException("No @DataEntity annotation found");

        Table table = clazz.getAnnotation(Table.class);
        String tableName;
        if(table == null) tableName = clazz.getSimpleName();
        else tableName = table.value();

        return tableName;
    }

    public static String getColumnName(Field field) {
        Column column = field.getAnnotation(Column.class);
        if (column != null) return column.value();
        else return field.getName();
    }
}
