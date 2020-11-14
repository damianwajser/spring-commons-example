package app.example.test.utils;

import app.example.test.rules.LocalDbCreationRule;
import com.TestApplication;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TestApplication.class)
@AutoConfigureMockMvc
@DirtiesContext
@Ignore
public class AbstractTest {

	@Autowired
	protected MockMvc mockMvc;

	@ClassRule
	public static LocalDbCreationRule dynamoDB = new LocalDbCreationRule(TestConstants.DYNAMO_TEST_PORT);

}
