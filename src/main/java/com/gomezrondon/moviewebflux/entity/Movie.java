package com.gomezrondon.moviewebflux.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Document // mongo
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@With
public class Movie {

    @Id
    private String id;

    @NotNull
    @NotEmpty(message = "The name of the movie can not be empty")
    private String title;

    public Movie(String title) {
        this.title = title;
        this.id = null;
    }
}
