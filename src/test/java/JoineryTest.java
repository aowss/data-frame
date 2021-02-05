import joinery.DataFrame;

import org.junit.jupiter.api.*;

import java.io.IOException;
import java.util.*;

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

        List<Integer> ages = dataFrame.col("Age");
        assertThat(ages, is(List.of(22, 35, 58)));

        DataFrame columns = dataFrame.retain("Age", "Sex");
        assertThat(columns.columns(), is(Set.of("Age", "Sex")));
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
