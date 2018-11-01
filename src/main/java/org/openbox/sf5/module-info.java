module org.openbox.sf5.SF5Spring {
    requires java.persistence;
    requires java.xml.bind;
    requires jackson.annotations;
    requires java.sql;
    requires validation.api;
    requires hibernate.validator;
    requires spring.beans;
    requires spring.core;
    requires spring.context;
    requires spring.tx;
    requires spring.web;
    requires spring.webmvc;
    requires spring.data.commons;
    requires spring.data.jpa;
}