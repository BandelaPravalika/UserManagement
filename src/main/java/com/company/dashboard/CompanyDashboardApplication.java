package com.company.dashboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableJpaAuditing
@EnableScheduling
@SpringBootApplication
@EnableAsync
@ComponentScan(basePackages = "com.company.dashboard")
public class CompanyDashboardApplication {

	public static void main(String[] args) {
		SpringApplication.run(CompanyDashboardApplication.class, args);
	}
}
