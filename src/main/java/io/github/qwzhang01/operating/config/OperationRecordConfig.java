package io.github.qwzhang01.operating.config;

import io.github.qwzhang01.operating.aspecrt.OpAspect;
import io.github.qwzhang01.operating.kit.SpringKit;
import io.github.qwzhang01.operating.processor.AfterProcessor;
import io.github.qwzhang01.operating.processor.BeforeProcessor;
import io.github.qwzhang01.operating.strategy.DefaultOpStrategy;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Auto-configuration for Operation Recording.
 * <p>
 * This configuration class automatically registers required beans for the
 * operation recording framework. It uses Spring Boot's auto-configuration
 * mechanism and only creates beans if they don't already exist in the context.
 *
 * <p>Registered beans:
 * <ul>
 *   <li>{@link SpringKit} - Utility for accessing Spring application
 *   context</li>
 *   <li>{@link DefaultOpStrategy} - Default strategy for operation
 *   recording</li>
 *   <li>{@link OpAspect} - AOP aspect for intercepting annotated methods</li>
 * </ul>
 *
 * <p>Users can override any of these beans by defining their own in the
 * application context, thanks to the {@link ConditionalOnMissingBean}
 * annotations.
 *
 * @author avinzhang
 */
@Configuration
public class OperationRecordConfig {

    /**
     * Creates a SpringKit bean if one doesn't already exist.
     * SpringKit provides convenient access to the Spring application context.
     *
     * @return a new SpringKit instance
     */
    @Bean
    @ConditionalOnMissingBean(SpringKit.class)
    public SpringKit opSpringKit() {
        return new SpringKit();
    }

    /**
     * Creates a DefaultOpStrategy bean if one doesn't already exist.
     * This is the default implementation for operation recording strategy.
     *
     * @return a new DefaultOpStrategy instance
     */
    @Bean
    @ConditionalOnMissingBean(DefaultOpStrategy.class)
    public DefaultOpStrategy defaultOpStrategy() {
        return new DefaultOpStrategy();
    }

    /**
     * Creates an OpAspect bean if one doesn't already exist.
     * This aspect intercepts methods annotated with
     * {@link io.github.qwzhang01.operating.anno.Op}
     * and orchestrates the operation recording process.
     *
     * @return a new OpAspect instance configured with before and after
     * processors
     */
    @Bean
    @ConditionalOnMissingBean(OpAspect.class)
    public OpAspect opAspect() {
        return new OpAspect(new BeforeProcessor(), new AfterProcessor());
    }
}
