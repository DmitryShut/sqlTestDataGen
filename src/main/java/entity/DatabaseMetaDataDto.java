package entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class DatabaseMetaDataDto {

    private List<TableMetaData> tablesMetaData = new ArrayList<>();

}
