package com.javaPlayground.multipleDataSource.config;

import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "com.javaPlayground.multipleDataSource.repository.employee",
        entityManagerFactoryRef = "employeeEntityManagerFactory",
        transactionManagerRef = "employeeTransactionManager"
)
public class EmployeeDataSourceConfig {

    @Bean(name = "employeeDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.employee")
    public DataSource employeeDataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Bean(name = "employeeEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean employeeEntityManagerFactory(
            @Qualifier("employeeDataSource") DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setPackagesToScan("com.javaPlayground.multipleDataSource.model.employee");
        factoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

        // Set Hibernate properties
        Map<String, Object> properties = new HashMap<>();
        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        properties.put("hibernate.hbm2ddl.auto", "update");
        factoryBean.setPersistenceUnitName("employee");
        factoryBean.setJpaPropertyMap(properties);

        return factoryBean;
    }

    @Bean(name = "employeeTransactionManager")
    public PlatformTransactionManager employeeTransactionManager(
            @Qualifier("employeeEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
