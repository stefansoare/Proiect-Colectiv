package com.project.pc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin
@SpringBootApplication
public class PcApplication {
	public static void main(String[] args) {
		try {
			SpringApplication.run(PcApplication.class, args);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
