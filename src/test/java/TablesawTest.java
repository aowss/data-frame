import tech.tablesaw.api.*;
import static tech.tablesaw.aggregate.AggregateFunctions.*;

import org.junit.jupiter.api.*;
import tech.tablesaw.columns.Column;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static tech.tablesaw.api.QuerySupport.and;

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

        List<String> columnNames = table.columnNames();
        assertThat(columnNames, is(List.of("Name","Age", "Sex")));

        Table age = table.select("Age");
        assertThat(age.columnNames(), is(List.of("Age")));

        Table columns = table.select("Age", "Sex");
        assertThat(columns.columnNames(), is(List.of("Age", "Sex")));

        Column ageColumn = table.column(1);
        assertThat(ageColumn.name(), is("Age"));

        List<Column<?>> columnsList = table.columns(1, 2);
        assertThat(columnsList.stream().map(Column::name).collect(Collectors.toList()), is(List.of("Age", "Sex")));
    }

    @Test
    @DisplayName("Filter rows")
    public void filter() {
        StringColumn names = StringColumn.create("Name", "Braund, Mr. Owen Harris", "Allen, Mr. William Henry", "Bonnell, Miss. Elizabeth");
        IntColumn ages = IntColumn.create("Age", 22, 35, 58);
        StringColumn sex =  StringColumn.create("Sex", "male", "male", "female");
        Table table = Table.create(names, ages, sex);

        Table over30 = table.where(table.intColumn("Age").isGreaterThan(30));
        assertThat(over30.rowCount(), is(2));

        Table malesOver30 = table
            .where(
                and(
                    t -> t.intColumn("Age").isGreaterThan(30),
                    t -> t.stringColumn("Sex").equalsIgnoreCase("male")
                )
            );
        assertThat(malesOver30.rowCount(), is(1));
    }

    @Test
    @DisplayName("Select Columns and Filtering rows")
    public void selectAndFilter() {
        StringColumn names = StringColumn.create("Name", "Braund, Mr. Owen Harris", "Allen, Mr. William Henry", "Bonnell, Miss. Elizabeth");
        IntColumn ages = IntColumn.create("Age", 22, 35, 58);
        StringColumn sex =  StringColumn.create("Sex", "male", "male", "female");
        Table table = Table.create(names, ages, sex);

        Table over30 = table
            .where(table.intColumn("Age").isGreaterThan(30))
            .select("Name", "Sex");
        assertThat(over30.rowCount(), is(2));
        assertThat(over30.columnCount(), is(2));

        Table malesOver30 = table
            .where(
                and(
                    t -> t.intColumn("Age").isGreaterThan(30),
                    t -> t.stringColumn("Sex").equalsIgnoreCase("male")
                )
            )
            .select("Name");
        assertThat(malesOver30.rowCount(), is(1));
        assertThat(malesOver30.columnCount(), is(1));
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
