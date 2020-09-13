package com.gomezrondon.moviewebflux;

import com.gomezrondon.moviewebflux.entity.Movie;
import com.gomezrondon.moviewebflux.service.MovieService;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.Result;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.blockhound.BlockHound;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class Application implements CommandLineRunner {

  // unit test does not work with blockhound
/*	static {
		BlockHound.install();
	}*/

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

		Flux<? extends Result> dropTable = Mono.from(connectionFactory.create())
				.flatMapMany(connection -> connection
						.createStatement("drop table movie;")
						.execute());


		Flux<? extends Result> createTable = Mono.from(connectionFactory.create())
				.flatMapMany(connection -> connection
								.createStatement(
										"" +
												"create table movie\n" +
										"(\n" +
										"    id   int auto_increment  PRIMARY KEY,\n" +
										"    name varchar(50) not null\n" +
										");"
								).execute());

/*		Flux<? extends Result> truncateTableMovie = Mono.from(connectionFactory.create())
				.flatMapMany(connection -> connection
						//.createStatement("ALTER TABLE movie AUTO_INCREMENT = 0")
						.createStatement("Truncate table movie")
						//.bind("$1", 300)
						.execute());*/

		Flux<Movie> matrixMovie = getMovieFlux(List.of("Matrix"));
		Flux<Movie> movieFlux = getMovieFlux(List.of("Terminator", "RoboCop", "Alien II", "RoboCop2","Batman Begins ", "Matrix 2", "Transformers", "Limitless"));

		dropTable
				.thenMany(createTable)
				.thenMany(service.deleteAll())	// delete all records
				.thenMany(matrixMovie) // to guaranty be the first
				.thenMany(movieFlux) 			 // insert all records
				//.thenMany(service.findAll()) 	// find all records
				.subscribe(System.out::println); // print all records

	}

	@NotNull
	private Flux<Movie> getMovieFlux(List<String> list) {
		return Flux.fromIterable(list)
				.map(String::toLowerCase)
				.map(title -> new Movie(null, title))
				.flatMap(service::save);
	}
}
