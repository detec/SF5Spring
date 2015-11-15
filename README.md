# Openbox SF-5 settings editor, Spring version #

This pet project derives from my old 1C:Enterprise 8.2 (<http://1c-dn.com/>) tool, released in 2010, for satellite television gadget, Openbox SF-5.
- <http://openbox.ua/instruments/sf5/>   - official page of the gadget;
- <http://infostart.ru/public/76804/>	 - page of the initial 1C:Enterprise 8.2 project.

Leaving behind satellite television details, Openbox SF-5 settings editor is a representation of a typical full-cycle CRUD application. It is able to:

- import catalogue data from structured text files (refreshed transponder data from resources like <http://ru.kingofsat.net>) into relational database;
- create and edit own entities (gadget settings) using catalogue data, store them in database and reuse when needed;
- export gadget settings into structured XML files for exchange with vendor owned gadget application;
- output user composed gadget settings to a print form, so that a user can have a hard copy of settings when using Openbox SF-5.

This implementation of Openbox SF-5 settings editor provides SQL-based user authentication (Spring Security 4 used) and registration, so that it can be run in a cloud. Each user can access only his/her own SF-5 settings. At the same time, a user has the right to update common catalogue with transponder data, without the need for the administrator to do this routine job. User administrator with credentials admin/1 is checked and, if necessary, created at every application startup.

When replicating this project from its 1C:Enterprise 8.2 original, I hardly tried to repeat in Spring MVC all GUI features that I had previously implemented in its original, 1C:Enterprise desktop version, to ensure good end-user experience. It includes:

- comfortable usage of transponder catalogue, single and multiple transponder selection;
- powerful feature of selection of settings lines from other settings while editing current setting;
- ability to move lines up and down in a setting.

In my humble opinion, I tried to do my best of pure Spring MVC, better then famous Spring pet clinic application, <http://docs.spring.io/docs/petclinic.html>

## System requirements ##

- Apache Tomcat 8 with H2 database jar in lib folder;
- H2 database server, run at the same host with Tomcat, default database URL is jdbc:h2:tcp://localhost/~/sf5spring
- Java 8.

## Technologies ##

- Spring 4 (Spring Core, Security, MVC, XML-based configuration);
- Hibernate ORM 4.3.11;
- Hibernate Validator 5.2.1;
- JUnit 4.12;
- JDBC (for Spring Security only);
- Apache Tomcat 8;
- Java 8.

The project can be built either with Maven (3.3 or higher) or Eclipse (4.5 or higher).