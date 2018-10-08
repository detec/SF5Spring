package org.openbox.sf5.config;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan({ "org.openbox.sf5" })
@Import({ SecurityConfiguration.class })
@ImportResource({ "/WEB-INF/security-context.xml" })
@EnableJpaRepositories(basePackages = "org.openbox.sf5.dao")
@EnableTransactionManagement
public class AppConfiguration {

    @Value("${jndi.datasource.name}")
    String jndiName;

    @Bean
    public DataSource dataSource() {
        JndiDataSourceLookup lookup = new JndiDataSourceLookup();
        try {
            return lookup.getDataSource(jndiName);
        } catch (Exception e) {
            DriverManagerDataSource dataSource = new DriverManagerDataSource();
            dataSource.setDriverClassName("org.h2.Driver");
            dataSource.setUrl("jdbc:h2:mem:db;DB_CLOSE_DELAY=-1");
            dataSource.setUsername("sa");
            dataSource.setPassword("sa");
            return dataSource;
        }
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();

        entityManagerFactoryBean.setDataSource(dataSource());
        entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        entityManagerFactoryBean.setPackagesToScan("org.openbox.sf5.model");

        Properties jpaProperties = new Properties();
        jpaProperties.put("hibernate.format_sql", "true");
        jpaProperties.put("hibernate.show_sql", "true");
        jpaProperties.put("hibernate.hbm2ddl.auto", "update");
        entityManagerFactoryBean.setJpaProperties(jpaProperties);

        return entityManagerFactoryBean;
    }

    @Bean
    public Jaxb2Marshaller jaxb2Marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setClassesToBeBound(org.openbox.sf5.model.Sat.class);

        Map<String, Object> properties = new HashMap<>();
        properties.put(javax.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT, true);
        properties.put(javax.xml.bind.Marshaller.JAXB_ENCODING, "UTF-8");
        properties.put(javax.xml.bind.Marshaller.JAXB_FRAGMENT, true);
        marshaller.setMarshallerProperties(properties);
        return marshaller;
    }

    @Bean
    public JpaTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return transactionManager;
    }

}
