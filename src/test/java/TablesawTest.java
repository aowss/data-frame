import tech.tablesaw.api.*;
import static tech.tablesaw.aggregate.AggregateFunctions.*;

import org.junit.jupiter.api.*;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@DisplayName("Tablesaw")
public class TablesawTest {

    @Test
    @DisplayName("Create a Table")
    public void create() {
        StringColumn names = StringColumn.create("Name", "Braund, Mr. Owen Harris", "Allen, Mr. William Henry", "Bonnell, Miss. Elizabeth");
        IntColumn ages = IntColumn.create("Age", 22, 35, 58);
        StringColumn sex =  StringColumn.create("Sex", "male", "male", "female");
        Table table = Table.create(names, ages, sex);

        System.out.println(table);
        System.out.println(table.structure());
        assertThat(table.columnNames(), is(List.of("Name", "Age", "Sex")));
    }

    @Test
    @DisplayName("Select Columns")
    public void select() {
        StringColumn names = StringColumn.create("Name", "Braund, Mr. Owen Harris", "Allen, Mr. William Henry", "Bonnell, Miss. Elizabeth");
        IntColumn ages = IntColumn.create("Age", 22, 35, 58);
        StringColumn sex =  StringColumn.create("Sex", "male", "male", "female");
        Table table = Table.create(names, ages, sex);

        Table age = table.select("Age");
        assertThat(age.columnNames(), is(List.of("Age")));

        Table columns = table.select("Age", "Sex");
        assertThat(columns.columnNames(), is(List.of("Age", "Sex")));
    }

    @Test
    @DisplayName("rounded average monthly close for the three top months of the year")
    public void stock() throws IOException {
        Table table = Table
                .read().csv(ClassLoader.getSystemResourceAsStream("stock.csv"));
        List<Integer> result = table
                .select("Date", "Close")
                .summarize("Close", mean).by(table.dateColumn("Date").month())
                .sortOn("-Mean [Close]")
                .first(3)
                .doubleColumn("Mean [Close]").asIntColumn()
                .asList();

        assertThat(result, is(List.of(1403, 1378, 1370)));
    }

}