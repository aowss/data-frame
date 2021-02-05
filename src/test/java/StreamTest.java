import org.apache.commons.csv.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.Charset;
import java.time.*;
import java.util.*;

import static java.util.stream.Collectors.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@DisplayName("Java 8")
public class StreamTest {

    @Test
    @DisplayName("rounded average monthly close for the three top months of the year")
    public void stock() throws IOException {
        CSVParser parser = CSVParser.parse(ClassLoader.getSystemResourceAsStream("stock.csv"), Charset.forName("UTF-8"), CSVFormat.DEFAULT.withFirstRecordAsHeader());

        List<Integer> result = parser.getRecords()
                .stream()
                .collect(groupingBy(record -> LocalDate.parse(record.get("Date")).getMonth(), averagingDouble(record -> Double.parseDouble(record.get("Close")))))
                .entrySet()
                .stream()
                .sorted(Map.Entry.<Month, Double>comparingByValue().reversed())
                .limit(3)
                .map(entry -> (int)Math.floor(entry.getValue()))
                .collect(toList());

        assertThat(result, is(List.of(1403, 1378, 1370)));
    }

}
