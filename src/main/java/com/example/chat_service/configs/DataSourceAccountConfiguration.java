package com.example.chat_service.configs;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "com.example.chat_service.repositoryes.account",
        entityManagerFactoryRef = "entity_manager_account",
        transactionManagerRef = "transaction_manager_account"
)
public class DataSourceAccountConfiguration {

    private final Environment environment;

    public DataSourceAccountConfiguration(Environment environment) {
        this.environment = environment;
    }

    private Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.show_sql", environment.getRequiredProperty("spring.jpa.show-sql"));
        return properties;
    }

    @Primary
    @Bean(name = "data_source_account")
    public DataSource dataSource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(environment.getProperty("spring.datasource.account.driver-class-name"));
        dataSource.setUrl(environment.getProperty("spring.datasource.account.url"));
        dataSource.setUsername(environment.getProperty("spring.datasource.account.username"));
        dataSource.setPassword(environment.getProperty("spring.datasource.account.password"));

        return dataSource;
    }

    @Primary
    @Bean(name = "entity_manager_account")
    LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(@Qualifier("data_source_account") DataSource dataSource){
        LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        localContainerEntityManagerFactoryBean.setDataSource(dataSource);
        localContainerEntityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        localContainerEntityManagerFactoryBean.setJpaProperties(hibernateProperties());
        localContainerEntityManagerFactoryBean.setPackagesToScan("com.example.chat_service.model.account");
        return localContainerEntityManagerFactoryBean;
    }

    @Primary
    @Bean(name = "transaction_manager_account")
    PlatformTransactionManager transactionManager(@Qualifier("entity_manager_account") LocalContainerEntityManagerFactoryBean entityManagerFactoryBean,
                                                  @Qualifier("data_source_account") DataSource dataSource){
        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
        jpaTransactionManager.setDataSource(dataSource);
        jpaTransactionManager.setEntityManagerFactory(entityManagerFactoryBean.getObject());
        return jpaTransactionManager;
    }
}
