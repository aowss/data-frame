import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import java.sql.*;
import java.util.stream.IntStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@DisplayName("SQL")
public class SQLTest {

    @Test
    @DisplayName("rounded average monthly close for the three top months of the year")
    public void stock() throws SQLException {
        try (Connection conn = DriverManager.getConnection("jdbc:h2:~/test", "sa", "")) {
            var stmt = conn.createStatement();
            String createTableSQL = "CREATE TABLE IF NOT EXISTS STOCK AS SELECT * FROM CSVREAD('classpath:stock.csv')";
            //String createTableSQL = "CREATE TABLE IF NOT EXISTS STOCK(Date DATE, Open REAL, High REAL, Low REAL, Close REAL, \"Adj Close\" REAL, Volume REAL) AS SELECT * FROM CSVREAD('classpath:stock.csv')";
//            String createTableSQL = "CREATE TABLE IF NOT EXISTS STOCK(Date DATE, Close REAL) AS SELECT Date, Close FROM CSVREAD('classpath:stock.csv')";
//            stmt.executeUpdate(createTableSQL);
//            String selectAllSQL = "SELECT * FROM STOCK";
//            ResultSet rs = stmt.executeQuery(selectAllSQL);
//            ResultSetMetaData rsmd = rs.getMetaData();
//            IntStream.range(1, rsmd.getColumnCount() + 1).forEach(index -> {
//                try {
//                    System.out.println(rsmd.getColumnName(index) + " is of type " + rsmd.getColumnTypeName(index));
//                } catch (SQLException throwables) {
//                    throwables.printStackTrace();
//                }
//            });
//            if (rs.next()) {
//                System.out.println(rs.getDate(0));
//            }
            //String selectSQL = "SELECT AVG(Close) FROM STOCK GROUP BY DATEPART(YEAR, Close), DATEPART(MONTH, Close) ORDER BY Close DESCENDING LIMIT 3";
            String selectSQL = "SELECT AVG(CAST(Close AS REAL)) FROM STOCK GROUP BY MONTH(PARSEDATETIME(Close, 'yyyy-MM-dd')) ORDER BY CAST(Close AS REAL) DESCENDING LIMIT 3";
            ResultSet rs1 = stmt.executeQuery(selectSQL);
            assertThat(rs1.next(), is(true));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
