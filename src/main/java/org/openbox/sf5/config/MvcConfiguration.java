package org.openbox.sf5.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.Ordered;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

// @EnableWebMvc
@Configuration
@ImportResource("/WEB-INF/sf5-servlet.xml")
public class MvcConfiguration extends WebMvcConfigurerAdapter {

	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

	@Bean
	public InternalResourceViewResolver getInternalResourceViewResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		// resolver.setPrefix("/WEB-INF/");
		resolver.setSuffix(".jsp");
		return resolver;
	}

	/*
	 * @Bean public SimpleControllerHandlerAdapter
	 * simpleControllerHandlerAdapter() { return new
	 * SimpleControllerHandlerAdapter(); }
	 */

	// It has already been defined in springWebMultiPartContext.
	// @Bean
	// public CommonsMultipartResolver multipartResolver() {
	// CommonsMultipartResolver resolver = new CommonsMultipartResolver();
	// resolver.setDefaultEncoding("utf-8");
	// resolver.setMaxUploadSize(1000000);
	// resolver.setMaxUploadSizePerFile(1000000);
	// return resolver;
	// }

	// It should replace welcome page
	/*
	 * @Override public void addViewControllers(ViewControllerRegistry registry)
	 * { registry.addViewController("/").setViewName("forward:/login.jsp"); }
	 */

	// http://docs.spring.io/spring-security/site/docs/4.0.3.RELEASE/guides/html5/form.html
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/login").setViewName("login");
		registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
	}

	// <bean id="contentNegotiationManager"
	// class="org.springframework.web.accept.ContentNegotiationManagerFactoryBean">
	// <property name="favorPathExtension" value="false" />
	// <property name="favorParameter" value="true" />
	// <property name="parameterName" value="mediaType" />
	// <property name="ignoreAcceptHeader" value="false"/>
	// <property name="useJaf" value="false"/>
	// <property name="defaultContentType" value="application/json" />
	//
	// <property name="mediaTypes">
	// <map>
	// <entry key="html" value="text/html" />
	// <entry key="json" value="application/json" />
	// <entry key="xml" value="application/xml" />
	// </map>
	// </property>
	// </bean>

	@Override
	public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
		configurer

				.favorPathExtension(false)

				.favorParameter(true)

				.parameterName("mediaType")

				.ignoreAcceptHeader(false)

				.useJaf(false)

				.defaultContentType(MediaType.APPLICATION_JSON)

				.mediaType("xml", MediaType.APPLICATION_XML)

				.mediaType("json", MediaType.APPLICATION_JSON)

				.mediaType("html", MediaType.TEXT_HTML);
		;
	}

	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {

		// registry.enableContentNegotiation(defaultViews);
	}

}
