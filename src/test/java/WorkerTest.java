import com.dmurchkov.parser.Worker;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class WorkerTest {

    @Mock
    Worker worker;

    @Test(expected = Exception.class)
    public void doWork_test() {
        Mockito.doThrow(new Exception()).when(worker).storeResults(anyMap());
        worker.doWork(anyString());
    }
}