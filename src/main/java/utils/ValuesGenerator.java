package utils;

import db.TestDataGenerator;
import entity.ColumnMetaData;
import lombok.Data;

import java.util.concurrent.ThreadLocalRandom;

import static db.TestDataGenerator.wrapInQuotes;
import static java.sql.Types.*;

@Data
public class ValuesGenerator {

    private static String lorem = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.";

    public static String generateLorem() {
        return lorem.substring(0, ThreadLocalRandom.current().nextInt(1, 40));
    }

    public static String generateIterationId() {
        return generateInteger(1, TestDataGenerator.REITERATION);
    }

    public static String generateInteger(int lowerBound, int upperBound) {
        return String.valueOf(ThreadLocalRandom.current().nextInt(lowerBound, upperBound + 1));
    }

    public static String generateBool() {
        return String.valueOf(ThreadLocalRandom.current().nextBoolean());
    }

    private static String generateTime() {
        return generateInteger(1, 24) + ":" + generateInteger(1, 60) + ":" + generateInteger(1, 60);
    }

    private static String generateDate() {
        return 2020 + "-" + generateInteger(0, 12) + "-" + generateInteger(0, 28);
    }

    private static String generateTimeZone() {
        return (Boolean.parseBoolean(generateBool()) ? "+" : "-") + generateInteger(0, 12);
    }

    private static String generateTimeWithoutTimeZone() {
        return "TIME WITHOUT TIME ZONE '" + generateTime() + "'";
    }

    private static String generateTimeWithTimeZone() {
        return "TIME WITH TIME ZONE '" + generateTime() + generateTimeZone() + "'";
    }

    private static String generateTimeStampWithTimeZone() {
        return "TIME WITH TIME ZONE '" + generateDate() + " " + generateTime() + generateTimeZone() + "'";
    }

    public static String generateValue(ColumnMetaData columnMetaData) {
        if (columnMetaData.getColumnType().equals("int8"))
            return generateIterationId();
        if (columnMetaData.getColumnType().equals("text"))
            return wrapInQuotes(generateLorem());
        if (columnMetaData.getColumnType().equals("bool"))
            return generateBool();
        if (columnMetaData.getColumnType().equals("float8"))
            return generateInteger(1, 90) + "." + generateInteger(1, 10000000);
        if (columnMetaData.getColumnType().equals("time"))
            return generateTimeWithoutTimeZone();
        if (columnMetaData.getColumnType().equals("timetz"))
            return generateTimeWithTimeZone();
        if (columnMetaData.getColumnType().equals("timestamptz"))
            return generateTimeStampWithTimeZone();
        if (columnMetaData.getColumnType().equals("date"))
            return wrapInQuotes(generateDate());
        if(columnMetaData.getColumnType().equals("serial"))
            return null;
        return "string";
    }

}
