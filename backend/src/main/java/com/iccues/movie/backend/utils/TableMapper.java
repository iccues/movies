package com.iccues.movie.backend.utils;

import com.iccues.movie.backend.utils.data.DataEntity;
import com.iccues.movie.backend.utils.data.Table;

import java.sql.DatabaseMetaData;
import java.sql.SQLException;

public class TableMapper {
    public static String getTableName(Class<?> clazz) {
        if(!clazz.isAnnotationPresent(DataEntity.class)) throw new RuntimeException("No @DataEntity annotation found");

        Table table = clazz.getAnnotation(Table.class);
        String tableName;
        if(table == null) tableName = clazz.getSimpleName();
        else tableName = table.value();

        return tableName;
    }

    public static<T> void syncTable(Class<T> clazz) throws SQLException {
        String tableName = getTableName(clazz);

        DatabaseMetaData metaData = DatabaseUtil.getConnection().getMetaData();

        // TODO
    }
}
