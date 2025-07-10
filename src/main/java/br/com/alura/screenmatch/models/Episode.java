/*
* Classe criada para representar episódios e permitir futuras operações na aplicação
* */

package br.com.alura.screenmatch.models;

import jakarta.persistence.*;

import java.time.DateTimeException;
import java.time.LocalDate;

@Entity
@Table(name = "episodios")
public class Episode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate dataLancamento;
    private Integer temporada;
    private String titulo;
    private Integer numeroEpisodio;
    private double avaliacao;

    @ManyToOne
    private Serie serie;

    public Episode(){}

    public Episode (Integer numeroTemporada, EpisodeData episodeData){
        this.temporada = numeroTemporada;
        this.titulo = episodeData.titulo();
        this.numeroEpisodio = episodeData.numeroEp();

        try{
            this.avaliacao = Double.valueOf(episodeData.avaliacao());
        } catch (NumberFormatException e) {
            this.avaliacao = 0.0;
        }

        try{
            this.dataLancamento = LocalDate.parse(episodeData.dataLancamento());
        } catch (DateTimeException e) {
            this.dataLancamento = null;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Serie getSerie() {
        return serie;
    }

    public void setSerie(Serie serie) {
        this.serie = serie;
    }

    public LocalDate getDataLancamento() {
        return dataLancamento;
    }

    public void setDataLancamento(LocalDate dataLancamento) {
        this.dataLancamento = dataLancamento;
    }

    public Integer getTemporada() {
        return temporada;
    }

    public void setTemporada(Integer temporada) {
        this.temporada = temporada;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getNumeroEpisodio() {
        return numeroEpisodio;
    }

    public void setNumeroEpisodio(Integer numeroEpisodio) {
        this.numeroEpisodio = numeroEpisodio;
    }

    public double getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(double avaliacao) {
        this.avaliacao = avaliacao;
    }

    @Override
    public String toString(){
        return "temporada = " + temporada +
                ", titulo = " + titulo +
                ", avaliacao = " + avaliacao +
                ", dataLancamento = " + dataLancamento;
    }

}
