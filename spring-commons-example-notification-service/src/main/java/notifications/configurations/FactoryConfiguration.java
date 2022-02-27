package notifications.configurations;

import com.github.damianwajser.factories.jsonbased.FactoryCriteriaJsonBased;
import com.github.damianwajser.factories.jsonbased.criteria.Criteria;
import com.github.damianwajser.factories.jsonbased.criteria.Criterion;
import com.github.damianwajser.factories.jsonbased.criteria.builder.CriterionBuilder;
import com.github.damianwajser.parsers.JsonParser;
import com.github.damianwajser.parsers.Mapper;
import notifications.model.NotificationConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.stream.Collectors;

@Configuration
public class FactoryConfiguration {

	private static Criterion<NotificationConfiguration> apply(NotificationConfiguration n) {
		Mapper mapper = new Mapper();
		n.getMapping().forEach(mapper::addMapping);
		n.setParser(new JsonParser(mapper));
		return CriterionBuilder.build(n.getCondition(), n);
	}

	@Bean
	public FactoryCriteriaJsonBased<NotificationConfiguration> factoryCriteriaJsonBased(NotificationProperties properties) {
		return new FactoryCriteriaJsonBased<>(new Criteria<>(properties.getNotifications()
				.values().stream()
				.map(FactoryConfiguration::apply)
				.collect(Collectors.toList())));
	}

}
