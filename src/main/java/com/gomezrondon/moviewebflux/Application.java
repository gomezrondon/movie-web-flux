package com.gomezrondon.moviewebflux;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application  {
  // unit test does not work with blockhound
/*	static {
		BlockHound.install();
	}*/

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
