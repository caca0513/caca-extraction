package caca.extraction.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"caca.extraction"})
public class PathFinderApplication {

	public static void main(String[] args) {
		SpringApplication.run(PathFinderApplication.class, args);
	}

}
