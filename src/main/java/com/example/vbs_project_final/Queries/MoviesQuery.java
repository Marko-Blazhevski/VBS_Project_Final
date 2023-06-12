package com.example.vbs_project_final.Queries;

import com.example.vbs_project_final.Models.Movie;
import org.apache.jena.query.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MoviesQuery {

    public List<Movie> queryMovies() {
        final List<Movie> movies = new ArrayList<>();

        ParameterizedSparqlString qs = new ParameterizedSparqlString(
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                        "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                        "PREFIX dbo: <http://dbpedia.org/ontology/>\n" +
                        "SELECT ?title ?releaseYear\n" +
                        "WHERE {\n" +
                        "  ?movie rdf:type dbo:Film .\n" +
                        "  ?movie rdfs:label ?title .\n" +
                        "  ?movie dbo:releaseDate ?releaseYear .\n" +
                        "  FILTER (LANG(?title) = 'en')\n" +
                        "}");


        QueryExecution exec = QueryExecutionFactory.sparqlService("http://dbpedia.org/sparql", qs.asQuery());

        ResultSet results = exec.execSelect();

        while (results.hasNext()) {
            QuerySolution el = results.next();
            movies.add(new Movie(el.get("title").toString().split("@")[0],el.get("releaseYear").toString().split("\\^")[0]));
        }
        return movies;
    }

    public List<Movie> findMovie(String searchTerm) {
        final List<Movie> movies = new ArrayList<>();
        ParameterizedSparqlString qs = new ParameterizedSparqlString(
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                        "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                        "PREFIX dbo: <http://dbpedia.org/ontology/>\n" +
                        "SELECT ?title ?releaseYear\n" +
                        "WHERE {\n" +
                        "  ?movie rdf:type dbo:Film .\n" +
                        "  ?movie rdfs:label ?title .\n" +
                        "  ?movie dbo:releaseDate ?releaseYear .\n" +
                        "  FILTER (LANG(?title) = 'en')\n" +
                        "  FILTER (CONTAINS(LCASE(?title), \""+searchTerm.toLowerCase()+"\")) .\n" +
                        "}");

        QueryExecution exec = QueryExecutionFactory.sparqlService("http://dbpedia.org/sparql", qs.asQuery());

        ResultSet results = exec.execSelect();

        while (results.hasNext()) {
            QuerySolution el = results.next();
            movies.add(new Movie(el.get("title").toString().split("@")[0],el.get("releaseYear").toString().split("\\^")[0]));
        }
        return movies;
    }

    public List<String> movieCategories() {
        final List<String> movieCategories = new ArrayList<>();

        ParameterizedSparqlString qs = new ParameterizedSparqlString(
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                        "PREFIX dct: <http://purl.org/dc/terms/>\n" +
                        "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                        "PREFIX dbpedia-owl: <http://dbpedia.org/ontology/>\n" +
                        "PREFIX dbo: <http://dbpedia.org/ontology/>\n" +
                        "PREFIX dbr: <http://dbpedia.org/resource/>\n" +
                        "\n" +
                        "SELECT ?genre\n" +
                        "WHERE {\n" +
                        "  ?movie rdf:type dbo:Film .\n" +
                        "  ?movie dbo:genre ?genre\n" +
                        "}"
        );


        QueryExecution exec = QueryExecutionFactory.sparqlService("http://dbpedia.org/sparql", qs.asQuery());

        ResultSet results = exec.execSelect();

        while (results.hasNext()) {
            QuerySolution el = results.next();
            movieCategories.add(el.get("genre").toString().split("resource/")[1]);
        }
        return movieCategories;
    }

    public List<Movie> moviesByCategory(String category){
        final List<Movie> movies = new ArrayList<>();
        ParameterizedSparqlString qs = new ParameterizedSparqlString(
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                        "PREFIX dct: <http://purl.org/dc/terms/>\n" +
                        "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                        "PREFIX dbpedia-owl: <http://dbpedia.org/ontology/>\n" +
                        "PREFIX dbo: <http://dbpedia.org/ontology/>\n" +
                        "PREFIX dbr: <http://dbpedia.org/resource/>\n" +
                        "\n" +
                        "SELECT ?movie ?title ?releaseYear\n" +
                        "WHERE {\n" +
                        "  ?movie rdf:type dbo:Film .\n" +
                        "  ?movie rdfs:label ?title .\n" +
                        "  ?movie dbo:releaseDate ?releaseYear .\n" +
                        "  ?movie dbo:genre <http://dbpedia.org/resource/"+category+">\n" +
                        "}"
        );
        QueryExecution exec = QueryExecutionFactory.sparqlService("http://dbpedia.org/sparql", qs.asQuery());

        ResultSet results = exec.execSelect();
        System.out.println(results.hasNext());
        while (results.hasNext()) {
            QuerySolution el = results.next();
            movies.add(new Movie(el.get("title").toString().split("@")[0],el.get("releaseYear").toString().split("\\^")[0]));
        }
        return movies;
    }
}
