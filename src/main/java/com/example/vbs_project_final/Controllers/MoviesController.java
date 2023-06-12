package com.example.vbs_project_final.Controllers;

import com.example.vbs_project_final.Models.Movie;
import com.example.vbs_project_final.Services.MoviesService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movies")
@AllArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000"})
public class MoviesController {

    private final MoviesService moviesService;
    @GetMapping()
    public List<Movie> getMovies() {
        return moviesService.getMovies();
    }

    @GetMapping("/search")
    public List<Movie> getMovie(@RequestParam String title) {
        return moviesService.searchMovie(title);
    }

    @GetMapping("/categories")
    public List<String> getMovieCategories() {
        return moviesService.movieCategories();
    }

    @GetMapping("/categories/search")
    public List<Movie> getMoviesByCategory(@RequestParam String category) {
        return moviesService.moviesByCategory(category);
    }
}
