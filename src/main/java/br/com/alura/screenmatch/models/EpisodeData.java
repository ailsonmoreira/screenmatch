package br.com.alura.screenmatch.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties (ignoreUnknown = true)
public record EpisodeData(@JsonAlias ("Title") String titulo,
                          @JsonAlias ("Episode") Integer numeroEp,
                          @JsonAlias ("imdbRating") String avaliacao,
                          @JsonAlias ("Released") String dataLancamento) {
}
