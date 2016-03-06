# Openbox SF-5 settings editor, Spring version #

This pet project derives from my old 1C:Enterprise 8.2 (<http://1c-dn.com/>) tool, released in 2010, for satellite television gadget, Openbox SF-5.
- <http://openbox.ua/instruments/sf5/>   - official page of the gadget;
- <http://infostart.ru/public/76804/>	 - page of the initial 1C:Enterprise 8.2 project.

## Openshift ##

This application is hosted in Openshift cloud. Its address is <http://sf5spring-detec.rhcloud.com/login>. Its database already has user logins 'admin' (password '1') and 'user' (password 'user') with one sample gadget setting saved in every profile. Anyone can register its own user and start creating Openbox SF-5 gadget settings.

## Features ##

Leaving behind satellite television details, Openbox SF-5 settings editor is a representation of a typical full-cycle CRUD application. It is able to:

- import catalogue data from structured text files (refreshed transponder data from resources like <http://ru.kingofsat.net>) into relational database;
- create and edit own entities (gadget settings) using catalogue data, store them in database and reuse when needed;
- export gadget settings into structured XML files for exchange with vendor owned gadget application;
- output user composed gadget settings to a print form, so that a user can have a hard copy of settings when using Openbox SF-5.

When replicating this project from its 1C:Enterprise 8.2 original, I hardly tried to repeat in Spring MVC all GUI features that I had previously implemented in its original, 1C:Enterprise desktop version, to ensure good end-user experience. It includes:

- comfortable usage of transponder catalogue, single and multiple transponder selection;
- powerful feature of selection of settings lines from other settings while editing current setting;
- ability to move lines up and down in a setting.

In my humble opinion, I tried to do my best of pure Spring MVC, better then well-known Spring pet clinic application, <http://docs.spring.io/docs/petclinic.html>

## User authentication ##

This implementation of Openbox SF-5 settings editor provides SQL-based user authentication (Spring Security 4 used) and registration, so that it can be run in a cloud. Each user can access only his/her own SF-5 settings. At the same time, a user has the right to update common catalogue with transponder data, without the need for the administrator to do this routine job. User administrator with credentials admin/1 is checked and, if necessary, created at every application startup. In JSP-mode CSRF protection is used.

There are 3 authentication modes:

- form-login for Spring MVC JSP pages;
- digest authentication for RESTful Spring MVC API;
- basic authentication for JAX-WS.

## REST service ##

This Openbox SF-5 settings editor implementation provides RESTful API for getting entities from database with the help of Spring MVC 4. For most endpoints Jackson 2 is used, however, for SF-5 XML format, where exact XML structure is required, Spring's Jaxb2Marshaller is used. Output format is resolved by client's "Accept" HTTP header, "application/json" or "application/xml". Here is the list of supported endpoints, relative to application context path:

- Satellites
	- jaxrs/satellites/all GET							- get all satellites;
	- jaxrs/satellites/filter/id/{satelliteId} GET 		- get satellite by its ID;
	- jaxrs/satellites/filter/{type}/{typeValue} GET 	- get satellites, filtered by arbitrary field name and field value.
	
- Transponders
	- jaxrs/transponders/filter/{type}/{typeValue} GET 	- get transponders, filtered by arbitrary field name and field value;
	- jaxrs/transponders/filter/id/{transponderId} GET 	- get transponder by its ID;
	- jaxrs/transponders/filter;satId={satId} GET 		- get all transponders from specified satellite;
	- jaxrs/transponders/all GET 						- get all transponders;
	- jaxrs/transponders/upload POST						- upload .ini file with transponders for further import. Content-type should be multipart/form-data.
	
- Users (most endpoints require digest authentication)
	- jaxrs/users/filter/username/{login} GET 			- get user by its login, for ADMIN role or user authenticated;
	- jaxrs/users/create POST 							- create new user, only for ADMIN role, new user ID is returned in "UserId" HTTP header; 
	- jaxrs/users/exists/username/{login} GET 			- check if such username exists, boolean value returned.
	
- OpenBox SF-5 settings (require digest authentication)
	- jaxrs/usersettings/create POST						- post user setting to this endpoint to create a new setting. User authenticated and the one in setting should coincide.
	- jaxrs/usersettings/all GET							- get all user's settings, based on credentials provided;
	- jaxrs/usersettings/filter/{type}/{typeValue} GET 		- get user's settings, filtered by arbitrary field name and field value, based on credentials provided;
	- jaxrs/usersettings/filter/id/{settingId} GET 			- get setting by its ID, based on credentials provided;
	- jaxrs/usersettings/filter/id/{settingId}/sf5 GET		- get setting by its ID, based on credentials provided, in Openbox SF-5 XML format; only "text/plain" "Accept" HTTP header is supported.
	
## JAX-WS service ##

The service name is OpenboxSF5Service, its WSDL can be obtained at /application_context_path/OpenboxSF5Service?wsdl . It is built with code-first approach and utilizes WildFly 10 built-in Apache CXF library. It uses all capabilities of JAX-RS service, that are responsible for data exchange, except Openbox SF-5 XML format output.
	
## Maven profiles ##

Different Maven profiles are required to use different database schemes and integration tests. Openbox SF-5 settings editor uses 3 maven profiles:

	- dev 	Default profile, database url is jdbc:h2:tcp://localhost/~/sf5springdev. 
			This database url is used in Eclipse-based container deploy.
	- test 	Profile for additional integration tests, run with Cargo Maven plugin in H2 in-memory mode;
	- prod 	Profile for production builds, database url is jdbc:h2:tcp://localhost/~/sf5spring.
	- openshift Profile for deployment in an OpenShift cloud.
	
## Tests notice ##

There are several JUnit tests, run in H2 in-memory mode. They check if Hibernate works with the database engine specified and if backend data processing features work, if SF-5 XML output matches the reference string. But as a former 1C:Enterprise developer I strongly believe that only real client-server environment can show if there are some errors with settings or annotations. That is why Cargo maven plugin is used in Maven test profile. Its paramount purpose is to test JAX-RS endpoints. Every aspect of Openbox SF-5 settings editor is tested: transponders upload and select, user creation and select, satellites select, user settings creation and select. Jersey 2 client is used in JAX-RS integration tests. For JAX-WS tests stub classes have been auto-generated by wsimport, integration tests repeat the ones for JAX-RS. Most important of the auto-generated stub classes are validated with constraints defined in XML files.

## Tomcat deploy ##

There is an option to utilize tomcat7-maven-plugin in pom.xml. Default administration url is http://localhost:8080/manager/text, with admin credentials admin/admin.
	
## System requirements ##

- configured non-XA datasource with JNDI name "java:jboss/datasources/PostgreSQLDS"; Postgre and H2 supported;
- WildFly 10 application server;
- Postgre 9.2+ database server, running at the same host with WildFly (for profiles dev, prod and openshift);
- Java 8.

## Technologies ##

- Spring 4 (Spring Core, Security, MVC, XML-based and Java configuration combined);
- Hibernate ORM 5.0.7;
- Hibernate POJO classes and mappings were generated from my 1C:Enterprise database using my 1C:Enterprise project <https://github.com/detec/POJOClassesGenerationForHibernate>;
- Hibernate Validator 5.2;
- Jackson 2.5;
- JUnit 4.12;
- JDBC (for Spring Security only);
- Jersey 2 client;
- Maven 3.3 with plugins compiler, surefire, resources, war, tomcat7, cargo, jaxws;
- WildFly 10;
- Java 8.

The project can be built either with Maven (3.3 or higher) or Eclipse (4.5 or higher).