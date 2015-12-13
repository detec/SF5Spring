# Openbox SF-5 settings editor, Spring version #

This pet project derives from my old 1C:Enterprise 8.2 (<http://1c-dn.com/>) tool, released in 2010, for satellite television gadget, Openbox SF-5.
- <http://openbox.ua/instruments/sf5/>   - official page of the gadget;
- <http://infostart.ru/public/76804/>	 - page of the initial 1C:Enterprise 8.2 project.

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

There are 2 authentication modes:

- login form for JSP-based Spring MVC controllers;
- digest authentication for RESTful Spring MVC API.

## REST service ##

This Openbox SF-5 settings editor implementation provides JAX-RS JSON API for getting entities from database with the help of Spring MVC 4. Here is the list of supported endpoints, relative to application context path:

- Satellites
	- json/satellites/all GET							- get all satellites as JSON Array;
	- json/satellites/filter/id/{satelliteId} GET 		- get satellite by its ID as JSON value;
	- json/satellites/filter/{type}/{typeValue} GET 	- get satellites, filtered by arbitrary field name and field value, as JSON Array.
	
- Transponders
	- json/transponders/filter/{type}/{typeValue} GET 	- get transponders, filtered by arbitrary field name and field value, as JSON Array;
	- json/transponders/filter/id/{transponderId} GET 	- get transponder by its ID as JSON value;
	- json/transponders/filter;satId={satId} GET 		- get all transponders from specified satellite as JSON Array;
	- json/transponders/all GET 						- get all transponders as JSON Array;
	- json/transponders/upload POST						- upload .ini file with transponders.
	
- Users (most endpoints require digest authentication)
	- json/users/filter/username/{login} GET 			- get user by its login as JSON value, for ADMIN role or user authenticated;
	- json/users/create POST 							- create new user, only for ADMIN role; 
	- json/users/exists/username/{login} GET 			- check if such username exists, boolean value returned.
	
- OpenBox SF-5 settings (requires digest authentication)
	- json/usersettings/create POST						- post user setting to this endpoint to create a new setting. User authenticated and the one in setting should coincide.
	- json/usersettings/all GET							- get all user's settings, based on credentials provided, as JSON Array;
	- json/usersettings/filter/{type}/{typeValue} GET 	- get user's settings, filtered by arbitrary field name and field value, based on credentials provided, as JSON Array;
	- json/usersettings/filter/id/{settingId} GET 		- get setting by its ID as JSON value, based on credentials provided.
	

## Maven profiles ##

Different Maven profiles are required to use different database schemes and integration tests. Openbox SF-5 settings editor uses 3 maven profiles:

	- dev 	Default profile, database url is jdbc:h2:tcp://localhost/~/sf5springdev. 
			This database url is used in Eclipse-based container deploy.
	- test 	Profile for additional integration tests, run with Cargo Maven plugin in H2 in-memory mode;
	- prod 	Profile for production builds, database url is jdbc:h2:tcp://localhost/~/sf5spring.
	
## Tests notice ##

There are several JUnit tests, run in H2 in-memory mode. They check if Hibernate works with the database engine specified and if backend data processing features work. But as a former 1C:Enterprise developer I strongly believe that only real client-server environment can show if there are some errors with settings or annotations. That is why Cargo maven plugin is used in Maven test profile. Its paramount purpose is to test JAX-RS endpoints. Every aspect of Openbox SF 5 settings editor is tested: transponders upload and select, user creation and select, satellites select, usersettings creation and select. Jersey 2 client is used in integration tests.

## Tomcat deploy ##

There is an option to utilize tomcat7-maven-plugin in pom.xml. Default administration url is http://localhost:8080/manager/text, with admin credentials admin/admin.
	
## System requirements ##

- Apache Tomcat 8;
- H2 database server, running at the same host with Tomcat (for profiles dev and prod);
- Java 8.

## Technologies ##

- Spring 4 (Spring Core, Security, MVC, XML-based configuration);
- Hibernate ORM 4.3.11;
- Hibernate POJO classes and mappings were generated from my 1C:Enterprise database using my 1C:Enterprise project <https://github.com/detec/POJOClassesGenerationForHibernate>;
- Hibernate Validator 5.2.1;
- JUnit 4.12;
- JDBC (for Spring Security only);
- Jersey 2 client;
- Maven 3.3 with plugins compiler, surefire, resources, war, tomcat7, cargo.
- Apache Tomcat 8;
- Java 8.

The project can be built either with Maven (3.3 or higher) or Eclipse (4.5 or higher).