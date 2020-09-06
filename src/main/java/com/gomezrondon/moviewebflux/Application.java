package com.gomezrondon.moviewebflux;

import com.gomezrondon.moviewebflux.entity.Movie;
import com.gomezrondon.moviewebflux.service.MovieService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class Application implements CommandLineRunner {
 // esto genera un error Connection unexpectedly closed
/*
	static {
		BlockHound.install();
	}
*/

	private final MovieService service;

	public Application(MovieService service) {
		this.service = service;
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		List<String> list = List.of("Matrix", "Terminator", "RoboCop", "Alien II", "RoboCop2",
				"Batman Begins ", "Matrix 2", "Transformers", "Limitless");
		Flux<Movie> movieFlux = Flux.fromIterable(list)
				.flatMap(title -> Flux.just(new Movie(null, title)))
				.flatMap(service::save);

		service.deleteAll().subscribe(null, null, () -> movieFlux.subscribe(System.out::println));



	}
}
