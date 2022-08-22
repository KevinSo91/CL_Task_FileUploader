package de.mainPackage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.context.embedded.tomcat.*;

@SpringBootApplication
public class FileUploaderApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(FileUploaderApplication.class, args);
	}
	
}
