package org.openbox.sf5.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

/**
 * Imports test beans
 *
 * @author Andrii Duplyk
 *
 */
@Configuration
@Import({ DatasourceConfiguration.class })
@ComponentScan({ "org.openbox.sf5.dao", "org.openbox.sf5.common", "org.openbox.sf5.service" })
public class AppTestConfiguration {

    @Bean
    public PropertySourcesPlaceholderConfigurer propertyConfigurer() {
        PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
        configurer.setIgnoreResourceNotFound(false);
        ClassPathResource resource = new ClassPathResource("application-test.properties");
        configurer.setLocation(resource);
        return configurer;
    }

    @Bean
    public Jaxb2Marshaller jaxb2Marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setClassesToBeBound(org.openbox.sf5.model.Sat.class);

        Map<String, Object> properties = new HashMap<>();
        properties.put(javax.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT, true);
        properties.put(javax.xml.bind.Marshaller.JAXB_ENCODING, "UTF-8");
        properties.put(javax.xml.bind.Marshaller.JAXB_FRAGMENT, true);
        marshaller.setMarshallerProperties(properties);
        return marshaller;
    }

}
