import joinery.DataFrame;

import org.junit.jupiter.api.*;
import tech.tablesaw.columns.Column;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@DisplayName("Joinery")
public class JoineryTest {

    @Test
    @DisplayName("Create a DataFrame")
    public void create() {
        DataFrame dataFrame = new DataFrame(
            Collections.emptyList(),
            List.of("Name", "Age", "Sex"),
            List.of(List.of("Braund, Mr. Owen Harris", "Allen, Mr. William Henry", "Bonnell, Miss. Elizabeth"), List.of(22, 35, 58), List.of("male", "male", "female"))
        );

        System.out.println(dataFrame);
        System.out.println(dataFrame.describe());
        assertThat(dataFrame.columns(), is(Set.of("Name", "Age", "Sex")));
    }

    @Test
    @DisplayName("Select Columns")
    public void select() {
        DataFrame dataFrame = new DataFrame(
            Collections.emptyList(),
            List.of("Name", "Age", "Sex"),
            List.of(List.of("Braund, Mr. Owen Harris", "Allen, Mr. William Henry", "Bonnell, Miss. Elizabeth"), List.of(22, 35, 58), List.of("male", "male", "female"))
        );

        Set<Object> columnNames = dataFrame.columns();
        assertThat(columnNames, is(Set.of("Name","Age", "Sex")));

        List<Integer> ages = dataFrame.col("Age");
        assertThat(ages, is(List.of(22, 35, 58)));

        DataFrame agesDF = dataFrame.retain("Age");
        assertThat(agesDF.columns(), is(Set.of("Age")));

        DataFrame columns = dataFrame.retain("Age", "Sex");
        assertThat(columns.columns(), is(Set.of("Age", "Sex")));

        DataFrame ageColumn = dataFrame.retain(1);
        assertThat(ageColumn.columns(), is(Set.of("Age")));

        DataFrame columnsList = dataFrame.retain(1,2);
        assertThat(columnsList.columns(), is(Set.of("Age", "Sex")));
    }

    @Test
    @DisplayName("Filter rows")
    public void filter() {
        DataFrame dataFrame = new DataFrame(
            Collections.emptyList(),
            List.of("Name", "Age", "Sex"),
            List.of(List.of("Braund, Mr. Owen Harris", "Allen, Mr. William Henry", "Bonnell, Miss. Elizabeth"), List.of(22, 35, 58), List.of("male", "male", "female"))
        );

        DataFrame.Predicate<Object> ageOver30 = values -> ((Integer)values.get(1)).intValue() > 30;
        DataFrame over30 = dataFrame.select(ageOver30);
        assertThat(over30.length(), is(2));

        DataFrame.Predicate<Object> maleAgeOver30 = values -> Integer.class.cast(values.get(1)).intValue() > 30 && ((String)values.get(2)).equals("male");
        DataFrame maleOver30 = dataFrame.select(maleAgeOver30);
        assertThat(maleOver30.length(), is(1));
    }

    @Test
    @DisplayName("rounded average monthly close for the three top months of the year")
    public void stock() throws IOException {
        List<Integer> result = DataFrame
                .readCsv(ClassLoader.getSystemResourceAsStream("stock.csv"))
                .retain("Date", "Close")
                .groupBy(row -> Date.class.cast(row.get(0)).getMonth())
                .mean()
                .sortBy("-Close")
                .head(3)
                .apply(value -> Number.class.cast(value).intValue())
                .col("Close");

        assertThat(result, is(List.of(1403, 1378, 1370)));
    }

}
