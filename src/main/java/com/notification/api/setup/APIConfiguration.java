package com.notification.api.setup;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(
		basePackages = {"com.notification.api.dao"}
)
@EnableTransactionManagement
public class APIConfiguration {

	@Value(value = "${app.env}")
	private String APP_ENV;
	
	@Bean(name = "devDB")
	@ConfigurationProperties(prefix = "api")
	public DataSource developmentDB() {
		return DataSourceBuilder.create().build();
	}
	
	@Bean("findmeSQL")
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
	    LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();	      
	    em.setPackagesToScan(new String[] { "com.notification.api.models" });
	    
	    switch (APP_ENV) {
			case "development":
				em.setDataSource(developmentDB());
				
				JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
			    em.setJpaVendorAdapter(vendorAdapter);
			    em.setJpaProperties(testProperties());
				break;
	
			default:
				em.setDataSource(developmentDB());
		}
	 
	    
	 
	    return em;
	}
	
	public Properties additionalProperties() {
		Properties properties = new Properties();
	    properties.setProperty("hibernate.hbm2ddl.auto", "");
	    properties.setProperty("hibernate.show_sql", "true");
	    properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");    
	    return properties;
	}
	
	public Properties testProperties() {
		Properties properties = new Properties();
	    properties.setProperty("hibernate.hbm2ddl.auto", "update");
	    properties.setProperty("hibernate.show_sql", "true");
	    properties.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");    
	    return properties;
	}
	
}
