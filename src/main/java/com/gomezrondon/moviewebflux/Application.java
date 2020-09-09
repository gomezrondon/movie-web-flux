package com.gomezrondon.moviewebflux;

import com.gomezrondon.moviewebflux.entity.Movie;
import com.gomezrondon.moviewebflux.service.MovieService;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.Result;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@SpringBootApplication
public class Application implements CommandLineRunner {
 // esto genera un error Connection unexpectedly closed
/*
	static {
		BlockHound.install();
	}
*/

	private final MovieService service;
	private final ConnectionFactory connectionFactory;

	public Application(MovieService service, @Qualifier("connectionFactory") ConnectionFactory connectionFactory) {
		this.service = service;
		this.connectionFactory = connectionFactory;
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		Flux<? extends Result> truncateTableMovie = Mono.from(connectionFactory.create())
				.flatMapMany(connection -> connection
						//.createStatement("ALTER TABLE movie AUTO_INCREMENT = 0")
						.createStatement("Truncate table movie")
						//.bind("$1", 300)
						.execute());


		Flux<Movie> movieFlux = Flux.just("Matrix", "Terminator", "RoboCop", "Alien II"
					, "RoboCop2","Batman Begins ", "Matrix 2", "Transformers", "Limitless")
 				.map(String::toLowerCase)
				.map(title -> new Movie(null, title))
				.flatMap(service::save);

		truncateTableMovie // truncate table to reset id counter
				.thenMany(service.deleteAll())	// delete all records
				.thenMany(movieFlux) 			 // insert all records
				//.thenMany(service.findAll()) 	// find all records
				.subscribe(System.out::println); // print all records

	}
}
