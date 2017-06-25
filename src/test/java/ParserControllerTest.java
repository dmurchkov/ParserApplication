import com.dmurchkov.parser.ParserController;
import com.dmurchkov.parser.ParserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;

import static org.mockito.Matchers.anyByte;
import static org.mockito.Matchers.anyString;

@RunWith(MockitoJUnitRunner.class)
public class ParserControllerTest {
    @Mock
    ParserService parserService;
    @InjectMocks
    ParserController parserController;

    @Test(expected = Exception.class)
    public void uploadFiles_test() throws IOException {
        Mockito.doThrow(new Exception()).when(parserService).submit(anyString());
        MockMultipartFile multipartFile[] = {new MockMultipartFile("file", new byte[]{anyByte()})};
        parserController.uploadFiles(multipartFile);
    }

    @Test(expected = Exception.class)
    public void getResults_test() {
        Mockito.doThrow(new Exception()).when(parserService).getResults();
        parserController.getResults();
    }
}
