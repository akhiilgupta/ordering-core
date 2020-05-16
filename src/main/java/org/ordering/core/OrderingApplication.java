package org.ordering.core;

import java.util.concurrent.Executor;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableScheduling
@EnableAutoConfiguration
@EnableAsync
public class OrderingApplication {
  public static void main(String[] args) {
    SpringApplication.run(OrderingApplication.class, args);
  }

  @Bean
  @Qualifier("customRestTemplate")
  public RestTemplate makeRestTemplate(RestTemplateBuilder builder) {
    PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
    connectionManager.setDefaultMaxPerRoute(20);
    HttpComponentsClientHttpRequestFactory requestFactory =
        new HttpComponentsClientHttpRequestFactory(
            HttpClientBuilder.create().setConnectionManager(connectionManager).build());
    requestFactory.setConnectTimeout(3000);
    requestFactory.setReadTimeout(500000);
    requestFactory.setConnectionRequestTimeout(5000);
    return builder.requestFactory(() -> requestFactory).build();
  }

  @Bean(name = "threadPoolTaskExecutor")
  public Executor asyncExecutor() {
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    executor.setCorePoolSize(5);
    executor.setDaemon(true);
    executor.setMaxPoolSize(10);
    executor.setQueueCapacity(500);
    executor.setThreadNamePrefix("async-");
    executor.initialize();
    return executor;
  }
}
