package io.github.qwzhang01.operating.config;

import io.github.qwzhang01.operating.kit.SpringKit;
import io.github.qwzhang01.operating.strategy.DefaultOpStrategy;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OperationRecordConfig {

    @Bean
    @ConditionalOnMissingBean(SpringKit.class)
    public SpringKit opSpringKit() {
        return new SpringKit();
    }

    @Bean
    @ConditionalOnMissingBean(DefaultOpStrategy.class)
    public DefaultOpStrategy defaultOpStrategy() {
        return new DefaultOpStrategy();
    }
}
