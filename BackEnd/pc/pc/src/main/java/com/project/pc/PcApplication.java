package com.project.pc;

import org.apache.catalina.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.stream.Stream;

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
