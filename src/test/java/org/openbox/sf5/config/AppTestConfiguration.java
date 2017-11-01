package org.openbox.sf5.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * Inports test beans
 *
 * @author Andrii Duplyk
 *
 */
@Configuration
@ImportResource({ "file:src/test/resources/context/test-autowired-beans.xml" })
public class AppTestConfiguration {

}
