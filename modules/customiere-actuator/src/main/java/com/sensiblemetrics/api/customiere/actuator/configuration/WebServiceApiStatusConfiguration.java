package com.sensiblemetrics.api.customiere.actuator.configuration;

import com.sensiblemetrics.api.customiere.actuator.indicator.StatefulHealthIndicator;
import com.sensiblemetrics.api.customiere.actuator.indicator.StatusInfoContributor;
import com.sensiblemetrics.api.customiere.actuator.indicator.TaskSchedulerHealthIndicator;
import com.sensiblemetrics.api.customiere.actuator.property.WebServiceApiStatusInfoProperty;
import com.sensiblemetrics.api.customiere.actuator.property.WebServiceApiStatusProperty;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.*;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Configuration
@Import(StatusInfoContributor.class)
@ConditionalOnProperty(prefix = WebServiceApiStatusProperty.PROPERTY_PREFIX, value = "enabled", havingValue = "true", matchIfMissing = true)
@EnableConfigurationProperties({WebServiceApiStatusProperty.class, WebServiceApiStatusInfoProperty.class})
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
@Description("SensibleMetrics Web Service Api Status configuration")
public abstract class WebServiceApiStatusConfiguration {

    @Bean
    @ConditionalOnBean(ThreadPoolTaskScheduler.class)
    @ConditionalOnClass(TaskSchedulerHealthIndicator.class)
    @Description("Actuator task scheduler health indicator configuration bean")
    public TaskSchedulerHealthIndicator taskSchedulerHealthIndicator(final ThreadPoolTaskScheduler taskScheduler) {
        return new TaskSchedulerHealthIndicator(taskScheduler);
    }

    @Bean
    @ConditionalOnClass(StatefulHealthIndicator.class)
    @Description("Actuator stateful health indicator configuration bean")
    public StatefulHealthIndicator statefulHealthIndicator() {
        return new StatefulHealthIndicator();
    }
}
