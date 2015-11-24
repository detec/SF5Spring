package org.openbox.sf5.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Configuration
@PropertySources(value = @PropertySource(value = { "classpath:application.properties" }) )
public class RestAPIConfig {
}

// http://javapapers.com/spring/spring-properties-with-propertysource-annotation/