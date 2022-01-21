package com.luv2code.springsecurity.demo.config;

import java.beans.PropertyVetoException;
import java.util.logging.Logger;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.mchange.v2.c3p0.ComboPooledDataSource;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.luv2code.springsecurity.demo")
@PropertySource("classpath:persistence-mysql.properties") // src/main/resources is the default filepath that Spring uses
															// // to hold the properties file
public class DemoAppConfig {

	// define Environment variable to hold props
	@Autowired
	private Environment env;

	// Logger for diagnostics
	private Logger log = Logger.getLogger(getClass().getName());

	// define a bean for view resolver
	@Bean
	public ViewResolver viewResolver() {

		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();

		viewResolver.setPrefix("/WEB-INF/view/");

		viewResolver.setSuffix(".jsp");

		return viewResolver;
	}

	// define bean for security data source

	@Bean
	public DataSource securityDataSource() {

		// create connection pool
		ComboPooledDataSource securityDataSource = new ComboPooledDataSource();

		// set the jdbc driver class
		try {

			securityDataSource.setDriverClass(env.getProperty("jdbc.driver"));

		} catch (PropertyVetoException e) {

			e.printStackTrace();
		}

		// log the connection props
		log.info(">>> jdbc.url=" + env.getProperty("jdbc.url"));
		log.info(">>> jdbc.user=" + env.getProperty("jdbc.user"));

		// set db connection props
		securityDataSource.setJdbcUrl(env.getProperty("jdbc.url"));
		securityDataSource.setUser(env.getProperty("jdbc.user"));
		securityDataSource.setPassword(env.getProperty("jdbc.password"));

		// set connection pool props
		securityDataSource.setInitialPoolSize(getIntProperty("connection.pool.initialPoolSize"));
		securityDataSource.setMinPoolSize(getIntProperty("connection.pool.minPoolSize"));
		securityDataSource.setMaxPoolSize(getIntProperty("connection.pool.maxPoolSize"));
		securityDataSource.setMaxIdleTime(getIntProperty("connection.pool.maxIdleTime"));

		return securityDataSource;
	}

	// helper method to convert String from properties file into an integer
	private int getIntProperty(String propName) {

		String propValString = env.getProperty(propName);

		int propValInt = Integer.parseInt(propValString);

		return propValInt;
	}

}
