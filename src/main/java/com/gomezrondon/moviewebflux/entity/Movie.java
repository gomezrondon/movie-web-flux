package com.gomezrondon.moviewebflux.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@With
@Table("movie")
public class Movie {

    @Id
    private Integer id;

    @NotNull
    @NotEmpty(message = "The name of the movie can not be empty")
    private String name;

    public Movie(String title) {
        this.name = title;
        this.id = null;
    }
}
