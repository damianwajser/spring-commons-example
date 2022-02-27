package notifications.test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static reactor.core.publisher.Mono.when;

import java.io.IOException;
import java.nio.charset.Charset;

import notifications.TestApplication;
import notifications.configuration.KafkaTestContainersConfiguration;
import notifications.senders.impl.PushSender;
import org.apache.commons.io.IOUtils;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.Resource;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.utility.DockerImageName;

@RunWith(SpringRunner.class)
@Import(KafkaTestContainersConfiguration.class)
@SpringBootTest(classes = TestApplication.class)
@DirtiesContext
public class KafkaTestContainersLiveTest {

	@ClassRule
	public static KafkaContainer kafka = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:5.4.3"));

	@Rule
	public MockitoRule initRule = MockitoJUnit.rule();

    @Autowired
    public KafkaTemplate<String, String> template;

    @Value("${test.topic}")
    private String[] topic;

	@SpyBean
	private PushSender sender;

	@Value("classpath:data/p2p.json")
	Resource resourceFile;

	private String getJson() throws IOException {
		return IOUtils.toString(resourceFile.getInputStream(), Charset.defaultCharset());
	}

    @Test
    public void givenKafkaDockerContainer_whenSending_to_DefaultTemplate_thenMessageReceived() throws Exception {
		for (String t:topic) {
			template.send(t, getJson());
		}
		verify(sender, timeout(5000).times(4)).send(any());
    }

}
