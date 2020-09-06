package entity;

import lombok.Data;

@Data
public class ColumnMetaData {

    private String columnName;

    private String columnType;

    private Boolean isPrimaryKey = false;

    private Boolean isAutoincrement = false;

    public ColumnMetaData(String columnName, String columnType, Boolean isAutoincrement) {
        this.columnName = columnName;
        this.columnType = columnType;
        this.isAutoincrement = isAutoincrement;
    }
}
