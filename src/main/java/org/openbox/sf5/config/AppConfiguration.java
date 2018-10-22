package org.openbox.sf5.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ComponentScan({ "org.openbox.sf5" })
@Import({ SecurityConfiguration.class, DatasourceConfiguration.class })
@ImportResource({ "/WEB-INF/security-context.xml" })
public class AppConfiguration {



}
