package com.run.holiday.config;

import com.run.holiday.dao.HolidayDao;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;
import org.springframework.web.client.RestTemplate;

import javax.sql.DataSource;

@Configuration
public class AppConfig {

  @Bean
  public Jdbi jdbi(DataSource ds) {
    TransactionAwareDataSourceProxy proxy = new TransactionAwareDataSourceProxy(ds);
    Jdbi jdbi = Jdbi.create(proxy);
    jdbi.installPlugin(new SqlObjectPlugin());
    return jdbi;
  }

  @Bean
  public HolidayDao meterDao(Jdbi jdbi) {
    return jdbi.onDemand(HolidayDao.class);
  }

  @Bean
  public RestTemplate restTemplate() {
    return new RestTemplateBuilder().build();
  }
}
