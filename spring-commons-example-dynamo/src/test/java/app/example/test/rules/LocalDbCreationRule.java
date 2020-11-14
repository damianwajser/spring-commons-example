package app.example.test.rules;

import com.amazonaws.services.dynamodbv2.local.main.ServerRunner;
import com.amazonaws.services.dynamodbv2.local.server.DynamoDBProxyServer;
import org.junit.rules.ExternalResource;

public class LocalDbCreationRule extends ExternalResource {
	private DynamoDBProxyServer server;

	private String port;

	public LocalDbCreationRule(String port) {
		this.port = port;
		System.setProperty("sqlite4java.library.path", "native-libs");
	}

	@Override
	protected void before() throws Exception {
		String dynamoUrl = System.getProperty("amazon.dynamodb.endpoint");

		server = ServerRunner.createServerFromCommandLineArgs(
				new String[]{"-inMemory", "-port", port});
		server.start();
	}

	@Override
	protected void after() {
		this.stopUnchecked(server);
	}

	protected void stopUnchecked(DynamoDBProxyServer dynamoDbServer) {
		try {
			dynamoDbServer.stop();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
