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
        basePackages = "com.example.chat_service.repositoryes.chat",
        entityManagerFactoryRef = "entity_manager_chat",
        transactionManagerRef = "transaction_manager_chat"
)
public class DataSourceChatConfiguration {

    private final Environment environment;

    public DataSourceChatConfiguration(Environment environment) {
        this.environment = environment;
    }

    private Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.show_sql", environment.getRequiredProperty("spring.jpa.show-sql"));
        return properties;
    }

    @Primary
    @Bean(name = "data_source_chat")
    public DataSource dataSource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setPassword(environment.getProperty("spring.datasource.chat.password"));
        dataSource.setUsername(environment.getProperty("spring.datasource.chat.username"));
        dataSource.setUrl(environment.getProperty("spring.datasource.chat.url"));
        dataSource.setDriverClassName(environment.getProperty("spring.datasource.chat.driver-class-name"));

        return dataSource;
    }

    @Primary
    @Bean(name = "entity_manager_chat")
    LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(@Qualifier("data_source_chat") DataSource dataSource){
        LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        localContainerEntityManagerFactoryBean.setDataSource(dataSource);
        localContainerEntityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        localContainerEntityManagerFactoryBean.setJpaProperties(hibernateProperties());
        localContainerEntityManagerFactoryBean.setPackagesToScan("com.example.chat_service.model.message");
        return localContainerEntityManagerFactoryBean;
    }

    @Primary
    @Bean(name = "transaction_manager_chat")
    PlatformTransactionManager transactionManager(@Qualifier("entity_manager_chat") LocalContainerEntityManagerFactoryBean entityManagerFactoryBean,
                                                  @Qualifier("data_source_chat") DataSource dataSource){
        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
        jpaTransactionManager.setDataSource(dataSource);
        jpaTransactionManager.setEntityManagerFactory(entityManagerFactoryBean.getObject());
        return jpaTransactionManager;
    }
}
