package entity;

import lombok.Data;

@Data
public class KeyMetaData {

    private String tableName;

    private String columnName;

    public KeyMetaData(String tableName, String columnName) {
        this.tableName = tableName;
        this.columnName = columnName;
    }
}
