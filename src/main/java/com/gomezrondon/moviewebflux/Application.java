package com.gomezrondon.moviewebflux;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.blockhound.BlockHound;

@SpringBootApplication
public class Application {
 // esto genera un error Connection unexpectedly closed
/*
	static {
		BlockHound.install();
	}
*/

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
