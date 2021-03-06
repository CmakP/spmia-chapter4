package com.thoughtmechanix.licenses;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import java.util.Collections;
import java.util.List;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class Application {

  /** To use a Ribbon-aware RestTemplate class, you need to define a Rest-Template bean
      construction method with a Spring Cloud annotation called @LoadBalanced.
      In addition, by using the RestTemplate class, Ribbon will round-robin load balance
      all requests among all the service instances.
   **/
  @LoadBalanced
  @Bean
  public RestTemplate getRestTemplate(){
      return new RestTemplate();
  }

  public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
  }
}
