#Spring commons example (notification service)

## Overview

In this example we are going to see how several spring commons utilities can be used together for the configurable sending of notifications.

Context:
1) N messages are received from Kafka
2) The messages are converted to a "Notification" object
3) Based on criteria related to the received message, it is chosen how to send the message.
4) The sending of the messages is simulated

Tools:
1) Kafka Test Containers
2) Mockito
3) Spring-commons-utilities:
   1) FactoryJsonBasedTest for the rules over configuration
   2) JsonToObjectConverter for convert the message to Notification.

## Let's Go!

### Test Environment
First we are going to configure the test environment and the kafka container.

````java
@TestConfiguration
public class KafkaTestContainersConfiguration {

	@Bean
	ConcurrentKafkaListenerContainerFactory<Integer, String> kafkaListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<Integer, String> factory = new ConcurrentKafkaListenerContainerFactory<Integer, String>();
		factory.setConsumerFactory(consumerFactory());
		return factory;
	}

	@Bean
	public ConsumerFactory<Integer, String> consumerFactory() {
		return new DefaultKafkaConsumerFactory<Integer, String>(consumerConfigs());
	}

	@Bean
	public Map<String, Object> consumerConfigs() {
		Map<String, Object> props = new HashMap<String, Object>();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaTestContainersLiveTest.kafka.getBootstrapServers());
		props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
		props.put(ConsumerConfig.GROUP_ID_CONFIG, "com.github.damianwajser");
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		return props;
	}

	@Bean
	public ProducerFactory<String, String> producerFactory() {
		Map<String, Object> configProps = new HashMap<String, Object>();
		configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaTestContainersLiveTest.kafka.getBootstrapServers());
		configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		return new DefaultKafkaProducerFactory<String, String>(configProps);
	}

	@Bean
	public KafkaTemplate<String, String> kafkaTemplate() {
		return new KafkaTemplate<String, String>(producerFactory());
	}
}
````
Write the test skeleton:
````java
@RunWith(SpringRunner.class)
@Import(KafkaTestContainersConfiguration.class)
@SpringBootTest(classes = TestApplication.class)
@DirtiesContext
public class KafkaTestContainersLiveTest {

    @ClassRule
    public static KafkaContainer kafka = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:5.4.3"));
}
````
### Create the model Configuration
In Application.yml
1) We configure the topics to which we are going to subscribe (separated by commas), then we are going to use it both, in the consumer (controller), and in the test to simulate the shipments.
```Yaml
subscribe:
  topics: send-many,purchase
```
2) The configurations:
   1) List of NotificationsConfigurations
      1) Condition for this notification to apply
      2) Information about how the message will be delivered
      3) Information on how the received message has to be transformed to the notification object
```yaml
notifications:
  send_money:
    condition: $.type = p2p
    sender:
      channel: push
    mapping:
      userid: $.userid
      title: ${user.name} send ${amount} to $.destination.user_name
      subtitle: send many
      footer:
  recived_money:
    condition: $.type = p2p
    sender:
      channel: push
    mapping:
      userid: $.user_id
      title: ${user_name} send ${amount} to $.destination.user_name
      subtitle: recived money
      footer: $.creation_date
  purchase:
    condition: $.type = p2m
    sender:
      channel: email
    mapping:
      userid: user en recived_money
      title: ${user.name} le mandaste  ${tx.amount} a $.destinatiion.user_name
      subtitle: ${user.name} asdasd ${asdasd}
      footer: ${user.name} asdasd ${asdasd}
```
3) The Model:

- List of NotificationsConfigurations
```java
@Configuration
@ConfigurationProperties
public class NotificationProperties {
	private Map<String, NotificationConfiguration> notifications;
    // getters and setters
}
```
- The NotificationsConfigurations:

You will see that we add a converter, so that each configuration has its own converter.

The mapping information is a map, where the key is the field and the value is the content to be transformed from the message, JsonPaths can be used to build it.
````java
public class NotificationConfiguration {

	private String condition;
	private SenderConfiguration sender;
	private Map<String,String> mapping;
	private JsonToObjectConverter converter;
	//Getters and setters
}
````
- SenderConfiguration
````java
public class SenderConfiguration {
	private Channel channel;
	// getters and setters
}
````
### The Model
1) Enumerate the channels
````java
public enum Channel {
	PUSH, EMAIL
}
````
2) The Object Notification
```java
public class Notification {
	private String userid;
	private String title;
	private String subtitle;
	private String body;
	private String footer;
	// each notification have own information to sent
	private SenderConfiguration sender;

	// getters and setters

	public Notification attachSender(SenderConfiguration sender) {
		this.sender = sender;
		return this;
	}
}
```
### The Logic to send
Senders and factories
```java
public interface Sender{
	void send(Notification n);
}
```
````java
@Service
public class EmailSender implements Sender {
	@Override
	public void send(Notification n) {
		System.err.println("send push notifications to: " + n.getUserid() + " with message: " + n);
	}
}
````
````java
@Service
public class PushSender implements Sender {
	@Override
	public void send(Notification n) {
		System.err.println("send push notifications to: " + n.getUserid() + " with message: " + n);
	}
}
````

````java

@Component
public class SenderFactory {
	@Autowired
	private PushSender pushSender;
	@Autowired
	private EmailSender emailSender;

	public Sender getNotificationSender(Channel channel) {
		return switch (channel) {
			case EMAIL -> emailSender;
			case PUSH, default -> pushSender;
		};
	}
}
````
### The magic of Spring-commons utilities

We created a search engine by criteria for the configuration
```java
@Configuration
public class FinderConfigurationInitializer {

	@Bean
	public FactoryCriteriaJsonBased<NotificationConfiguration> finderConfiguration(NotificationProperties properties) {
		return new FactoryCriteriaJsonBased<>(createCriteria(properties));
	}

	private Criteria<NotificationConfiguration> createCriteria(NotificationProperties properties) {
		return new Criteria<>(convertConfigurationsToListOfCriterion(properties));
	}

	private List<Criterion<NotificationConfiguration>> convertConfigurationsToListOfCriterion(NotificationProperties properties) {
		return properties.getNotificationConfigurations().values().stream()
				.map(this::toCriterion)
				.collect(Collectors.toList());
	}

	/**
	 * convert NotificationConfiguration to Criterion for find configuration
	 * */
	private Criterion<NotificationConfiguration> toCriterion(NotificationConfiguration n) {
		addConverterToConfiguration(n);
		return CriterionBuilder.build(n.getCondition(), n);
	}

	/**
	 * complete the notification Object with JsonToObjectConverter toUse
	 * */
	private void addConverterToConfiguration(NotificationConfiguration n) {
		Mapper mapper = new Mapper();
		n.getMapping().forEach(mapper::addMapping);
		n.setConverter(new JsonToObjectConverter(mapper));
	}
}
```
### The Controller
````java
@Service
public class NotificationController {

	private static final Logger LOGGER = LoggerFactory.getLogger(NotificationController.class);

	@Autowired
	private SenderFactory senderFactory;

	@Autowired
	private FactoryCriteriaJsonBased<NotificationConfiguration> configurationFinder;

	@KafkaListener(topics = "#{'${subscribe.topics}'.split(',')}")
	public void receive(String message) {
		LOGGER.debug("received: {}", message);
		DocumentContext json = JsonPath.parse(message);
		configurationFinder.find(json).stream()
				.map(n -> this.toNotification(n, json))
				.forEach(this::sendMessage);
	}

	private void sendMessage(Notification n) {
		LOGGER.debug("send notification: {}", n);
		senderFactory.getNotificationSender(n.getSender().getChannel()).send(n);
	}

	private Notification toNotification(NotificationConfiguration c, DocumentContext json) {
		try {
			LOGGER.debug("transform notification: {}", c);
			return c.getConverter().convert(json, Notification.class).attachSender(c.getSender());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
````
