package com.microservices.registerservice;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer; 
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.netflix.discovery.shared.Application;
import com.netflix.eureka.registry.PeerAwareInstanceRegistry;

@SpringBootApplication
@EnableEurekaServer
@EnableScheduling
public class RegisterserviceApplication {

	@Autowired
	PeerAwareInstanceRegistry eureka_registry;

	public static void main(String[] args) {
		SpringApplication.run(RegisterserviceApplication.class, args);
	}

	@Scheduled(fixedRate = 1000)
	public void showClientStatus()
	{
		/**
		 * Checking the status of each registered client every fixedRate milliseconds
		 * If a client status down is detected, notify the service factory.
		 */
		List<Application> apps = eureka_registry.getSortedApplications();
		System.out.println("Current time: " + (new Date()));
		for(Application a : apps)
		{
			System.out.println("  " + a.getName() + ": " + a.getInstancesAsIsFromEureka().get(0).getStatus().name());
		}
	}

}
