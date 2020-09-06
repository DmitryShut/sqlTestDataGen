package entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class TableMetaData {

    private String tableName;

    private Map<String, ColumnMetaData> nameToColumnMeta = new HashMap<>();

    private Map<String, KeyMetaData> importedKeysMetaData = new HashMap<>();

    private Map<String, KeyMetaData> exportedKeysMetaData = new HashMap<>();

    @Getter(AccessLevel.NONE)
    private Boolean isIndependent;

    public TableMetaData(String tableName) {
        this.tableName = tableName;
    }

    public Boolean getIndependent() {
        return importedKeysMetaData.isEmpty();
    }
}
