import com.dmurchkov.parser.ParserService;
import com.dmurchkov.parser.WordCount;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ParserServiceTest {
    @Mock
    ExecutorService executorService;
    @Mock
    ApplicationContext applicationContext;
    @Mock
    JdbcTemplate jdbcTemplate;
    @InjectMocks
    ParserService parserService;

    @Test
    public void getResults_test() {

        List<Map<String, Object>> rows = new ArrayList<>();
        Map<String, Object> row_1 = new HashMap<>();
        row_1.put("word", "row_1");
        row_1.put("count", 1);
        rows.add(row_1);

        when(jdbcTemplate.queryForList(anyString())).thenReturn(rows);

        assertEquals(parserService.getResults().get(0), new WordCount("row_1", 1));
    }

    @Test(expected = Exception.class)
    public void submit_test() {
        Mockito.doThrow(new Exception()).when(executorService).submit(new Runnable() {
            @Override
            public void run() {
            }
        });
        parserService.submit(anyString());
    }
}
