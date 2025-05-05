package com.tanzu.rewrite.demo.ips_are_hard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;

@SpringBootApplication
public class IpsAreHardApplication {

    public static void main(String[] args) {
        SpringApplication.run(IpsAreHardApplication.class, args);
    }

    @Bean
    @DependsOn("apiController")
    public ApiHealthCheck apiHealthCheck() {
        var apiHealthCheck = new ApiHealthCheck();
        apiHealthCheck.startChecking(5000);
        return apiHealthCheck;
    }
}
