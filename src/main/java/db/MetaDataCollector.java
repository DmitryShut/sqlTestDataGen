package db;

import entity.ColumnMetaData;
import entity.DatabaseMetaDataDto;
import entity.KeyMetaData;
import entity.TableMetaData;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Comparator;

public class MetaDataCollector {

    public DatabaseMetaDataDto collect() {
        DatabaseMetaDataDto databaseMetaDataDto = new DatabaseMetaDataDto();
        try (Connection conn = DataSource.getConnection()) {
            DatabaseMetaData meta = conn.getMetaData();
            String catalog = null, schemaPattern = null, tableNamePattern = null;
            String[] types = {"TABLE"};
            ResultSet rsTables = meta.getTables(catalog, schemaPattern, tableNamePattern, types);
            while (rsTables.next()) {
                String tableName = rsTables.getString(3);
                if (tableName.contains("databasechangelog"))
                    continue;
                TableMetaData tableMetaData = new TableMetaData(tableName);
                ResultSet rsColumns = meta.getColumns(catalog, schemaPattern, tableName, null);
                ResultSet rsPK = meta.getPrimaryKeys(catalog, schemaPattern, tableName);
                ResultSet rsEK = meta.getExportedKeys(catalog, schemaPattern, tableName);
                ResultSet rsIK = meta.getImportedKeys(catalog, schemaPattern, tableName);
                while (rsColumns.next()) {
                    String columnName = rsColumns.getString("COLUMN_NAME");
                    String typeName = rsColumns.getString("TYPE_NAME");
                    String isAutoincrement = rsColumns.getString("IS_AUTOINCREMENT");
                    tableMetaData.getNameToColumnMeta().put(columnName, new ColumnMetaData(columnName, typeName, isAutoincrement.equals("YES")));
                }
                while (rsPK.next()) {
                    ColumnMetaData columnMetaData = tableMetaData.getNameToColumnMeta().get(rsPK.getString("COLUMN_NAME"));
                    if (columnMetaData != null)
                        columnMetaData.setIsPrimaryKey(true);
                }
                while (rsEK.next()) {
                    String fkTableName = rsEK.getString("FKTABLE_NAME");
                    String fkColumnName = rsEK.getString("FKCOLUMN_NAME");
                    tableMetaData.getExportedKeysMetaData().put(fkTableName, new KeyMetaData(fkTableName, fkColumnName));
                }
                while (rsIK.next()) {
                    String fkTableName = rsIK.getString("FKTABLE_NAME");
                    String fkColumnName = rsIK.getString("FKCOLUMN_NAME");
                    tableMetaData.getExportedKeysMetaData().put(fkTableName, new KeyMetaData(fkTableName, fkColumnName));
                }
                databaseMetaDataDto.getTablesMetaData().add(tableMetaData);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        databaseMetaDataDto.getTablesMetaData().sort(Comparator.comparing(TableMetaData::getIndependent));
        return databaseMetaDataDto;
    }

}
