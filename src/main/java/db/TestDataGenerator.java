package db;

import entity.ColumnMetaData;
import entity.DatabaseMetaDataDto;
import entity.TableMetaData;
import utils.ValuesGenerator;

import java.util.*;
import java.util.stream.Collectors;

public class TestDataGenerator {

    public static final int REITERATION = 20;

    public List<String> generate(DatabaseMetaDataDto databaseMetaDataDto) {
        List<String> strings = new ArrayList<>();
        databaseMetaDataDto.getTablesMetaData().forEach(tableMetaData -> {
            for (int i = 0; i < REITERATION; i++) {
                String insertString = generateInsert(tableMetaData.getTableName(),
                        tableMetaData.getNameToColumnMeta().keySet(),
                        tableMetaData.getNameToColumnMeta().values().stream()
                                .map(ValuesGenerator::generateValue)
                                .filter(Objects::nonNull)
                                .collect(Collectors.toList())
                );
                strings.add(insertString);
            }
            strings.add("");
        });
        return strings;
    }

    private String generateInsert(String tableName, Collection<String> columnNames, Collection<String> columnValues) {
        return "INSERT INTO " + wrapInDoubleQuotes(tableName) +
                " (" + String.join(",", columnNames) + ")" +
                " VALUES (" + String.join(",", columnValues) + ");";
    }

    public static String wrapInDoubleQuotes(String stringToWrap) {
        return wrap(stringToWrap, "\"");
    }

    public static String wrapInQuotes(String stringToWrap) {
        return wrap(stringToWrap, "'");
    }

    public static String wrap(String stringToWrap, String wrapper) {
        return wrapper + stringToWrap + wrapper;
    }

}
