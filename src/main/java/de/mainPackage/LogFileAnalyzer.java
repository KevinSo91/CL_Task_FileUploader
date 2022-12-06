package de.mainPackage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
//@EnableConfigurationProperties(ConfigProperties.class)
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


