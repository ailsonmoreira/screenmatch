/*
* Representa uma serie retornada pela API. Utiliza JsonAlias para mapear nomes diferentes dos definidos no Json
* e JsonsIgnoraProperties para ignorar atributos não definidos aqui.
*/

package br.com.alura.screenmatch.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public record SerieData(@JsonAlias("Title") String titulo,
                        @JsonAlias("totalSeasons") Integer totalTemporadas,
                        @JsonAlias("imdbRating") String avaliacao,
                        @JsonAlias("Genre") String genero,
                        @JsonAlias("Actors") String atores,
                        @JsonAlias("Poster") String poster,
                        @JsonAlias("Plot") String sinopse) {
}
