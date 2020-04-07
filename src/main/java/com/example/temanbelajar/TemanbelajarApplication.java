package com.example.temanbelajar;

import com.example.temanbelajar.config.property.FileStorageProperties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({
	FileStorageProperties.class
})

public class TemanbelajarApplication {

	public static void main(String[] args) {
		SpringApplication.run(TemanbelajarApplication.class, args);
	}

}
