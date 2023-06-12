package com.example.vbs_project_final.Services;

import com.example.vbs_project_final.Models.Movie;
import com.example.vbs_project_final.Queries.MoviesQuery;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MoviesService {
    private final MoviesQuery moviesQuery;
    public List<Movie> getMovies() {
        return moviesQuery.queryMovies();
    }

    public List<Movie> searchMovie(String title) {
        return moviesQuery.findMovie(title);
    }

    public List<String> movieCategories() {
        return moviesQuery.movieCategories();
    }

    public List<Movie> moviesByCategory(String category){
        return moviesQuery.moviesByCategory(category);
    }

}
