package org.openbox.sf5.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;

@Configuration
// @ComponentScan({})
@Import({ MvcConfiguration.class

		// , SecurityConfiguration.class,
		// WebFlowConfig.class

})
@ImportResource({ "classpath:/root-context.xml", "classpath:/springWebMultipartContext.xml",
		"classpath:/security-context.xml" })
public class AppConfiguration {

	// All taken from the great tutorial
	// http://codehustler.org/blog/spring-security-tutorial-form-login-java-config/

}
