package com.linka39.conf;

import org.elasticsearch.client.Client;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;

/**
 * Created by wuwf on 17/6/2.
 */
@Configuration
public class ElasticSearchConfig {

	@Bean
	public ElasticsearchTemplate elasticsearchTemplate(Client client) {
		return new ElasticsearchTemplate(client);
	}

}
