package de.mainPackage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import de.mainPackage.config.ConfigProperties;
import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
@EnableConfigurationProperties(ConfigProperties.class)
public class LogFileAnalyzer {
	
	
	public static void main(String[] args) {
		SpringApplication.run(LogFileAnalyzer.class, args);
		log.info("LogFileAnalyzer started");
		
	}
	
}

@Configuration
@EnableScheduling
// Falls Scheduling disabled werden soll, muss dies in der Property explizit angegeben werden
@ConditionalOnProperty(name = "scheduling.enable", matchIfMissing = true)
class SchedulingConfiguration{	
}


