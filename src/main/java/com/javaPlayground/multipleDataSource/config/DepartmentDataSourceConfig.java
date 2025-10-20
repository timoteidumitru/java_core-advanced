package com.javaPlayground.multipleDataSource.config;

import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
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
        basePackages = "com.javaPlayground.multipleDataSource.repository.department",
        entityManagerFactoryRef = "departmentEntityManagerFactory",
        transactionManagerRef = "departmentTransactionManager"
)
public class DepartmentDataSourceConfig {
    @Primary
    @Bean(name = "departmentDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.department")
    public DataSource departmentDataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Primary
    @Bean(name = "departmentEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean departmentEntityManagerFactory(
            @Qualifier("departmentDataSource") DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setPackagesToScan("com.javaPlayground.multipleDataSource.model.department");
        factoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

        // Set Hibernate properties
        Map<String, Object> properties = new HashMap<>();
        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        properties.put("hibernate.hbm2ddl.auto", "update");
        factoryBean.setPersistenceUnitName("department");
        factoryBean.setJpaPropertyMap(properties);

        return factoryBean;
    }

    @Primary
    @Bean(name = "departmentTransactionManager")
    public PlatformTransactionManager departmentTransactionManager(
            @Qualifier("departmentEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
