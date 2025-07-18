package br.com.alura.screenmatch.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;

@Entity
@Table (name = "series")
public class Serie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String titulo;
    private Integer totalTemporadas;
    private Double avaliacao;
    @Enumerated(EnumType.STRING)
    private Categoria genero;
    private String atores;
    private String poster;
    private String sinopse;


    @OneToMany(mappedBy = "serie", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Episode> episodios = new ArrayList<>();

    public Serie(){}

    public Serie(SerieData serieData){
        this.titulo = serieData.titulo();
        this.totalTemporadas = serieData.totalTemporadas();
        this.avaliacao = OptionalDouble.of(Double.valueOf(serieData.avaliacao())).orElse(0);
        this.genero = Categoria.fromString(serieData.genero().split(",")[0].trim());
        this.atores = serieData.atores();
        this.poster = serieData.poster();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Episode> getEpisodios() {
        return episodios;
    }

    public void setEpisodios(List<Episode> episodios) {
        episodios.forEach(e -> e.setSerie(this));
        this.episodios = episodios;


    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getTotalTemporadas() {
        return totalTemporadas;
    }

    public void setTotalTemporadas(Integer totalTemporadas) {
        this.totalTemporadas = totalTemporadas;
    }

    public Double getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(Double avaliacao) {
        this.avaliacao = avaliacao;
    }

    public Categoria getGenero() {
        return genero;
    }

    public void setGenero(Categoria genero) {
        this.genero = genero;
    }

    public String getAtores() {
        return atores;
    }

    public void setAtores(String atores) {
        this.atores = atores;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getSinopse() {
        return sinopse;
    }

    public void setSinopse(String sinopse) {
        this.sinopse = sinopse;
    }

    @Override
    public String toString() {
        return
                "genero=" + genero +
                        ", titulo='" + titulo + '\'' +
                        ", totalTemporadas=" + totalTemporadas +
                        ", avaliacao=" + avaliacao +

                        ", atores='" + atores + '\'' +
                        ", poster='" + poster + '\'' +
                        ", sinopse='" + sinopse + '\'' +
                        ", episodio='" + episodios + '\'';
    }
}
