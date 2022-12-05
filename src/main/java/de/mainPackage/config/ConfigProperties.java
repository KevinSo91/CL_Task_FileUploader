package de.mainPackage.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@ConfigurationProperties(prefix = "logfiles") 
@ConfigurationPropertiesScan 
@Data
//@Validated
public class ConfigProperties { 
	
//	@NotNull
    private String defaultDirectory;
    
}