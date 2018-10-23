package org.openbox.sf5.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;

/**
 * Imports test beans
 *
 * @author Andrii Duplyk
 *
 */
@Configuration
@Import({ DatasourceConfiguration.class })
@ComponentScan({ "org.openbox.sf5.dao", "org.openbox.sf5.common" })
public class AppTestConfiguration {

    @Bean
    public PropertySourcesPlaceholderConfigurer propertyConfigurer() {
        PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
        configurer.setIgnoreResourceNotFound(false);
        ClassPathResource resource = new ClassPathResource("application-test.properties");
        configurer.setLocation(resource);
        return configurer;
    }

}
