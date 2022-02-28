package notifications.configurations;

import com.github.damianwajser.factories.jsonbased.FactoryCriteriaJsonBased;
import com.github.damianwajser.factories.jsonbased.criteria.Criteria;
import com.github.damianwajser.factories.jsonbased.criteria.Criterion;
import com.github.damianwajser.factories.jsonbased.criteria.builder.CriterionBuilder;
import com.github.damianwajser.parsers.JsonToObjectConverter;
import com.github.damianwajser.parsers.Mapper;
import notifications.configurations.model.NotificationConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.stream.Collectors;

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
