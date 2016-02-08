package org.openbox.sf5.config;

import javax.servlet.Filter;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class SpringConfigurationInitializer extends

		AbstractAnnotationConfigDispatcherServletInitializer

// AbstractDispatcherServletInitializer

{

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] { AppConfiguration.class };
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[] { MvcConfiguration.class };
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}

	// next is added from
	// http://stackoverflow.com/questions/23892140/spring-jsf-integration-pure-java-config-no-web-xml
	@Override
	protected Filter[] getServletFilters() {
		return new Filter[] { new CharacterEncodingFilter() };
	}

	// @Override
	// public void onStartup(ServletContext servletContext) throws
	// ServletException {
	// // XmlWebApplicationContext appContext = new XmlWebApplicationContext();
	// // appContext.setConfigLocation("/WEB-INF/sf5-servlet.xml");
	// //
	// // ServletRegistration.Dynamic registration =
	// // servletContext.addServlet("dispatcher",
	// // new DispatcherServlet(appContext));
	// // registration.setLoadOnStartup(1);
	// // registration.addMapping("/*");
	//
	// // XmlWebApplicationContext appContext = new XmlWebApplicationContext();
	// // appContext.setConfigLocation("classpath:/root-context.xml");
	//
	// super.onStartup(servletContext);
	// }
}
