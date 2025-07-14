package br.com.alura.screenmatch.services;

import br.com.alura.screenmatch.dto.SerieDTO;
import br.com.alura.screenmatch.models.Serie;
import br.com.alura.screenmatch.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SerieServices {

    @Autowired
    private SerieRepository repository;

    public List<SerieDTO> getAllSeries(){
        return dataFormat(repository.findAll());
    }

    public List<SerieDTO> getTop5Series() {
        return dataFormat(repository.findTop5ByOrderByAvaliacaoDesc());
    }

    private List<SerieDTO> dataFormat(List<Serie> series){
        return series.stream().map(s-> new SerieDTO(s.getId(), s.getTitulo(), s.getTotalTemporadas(), s.getAvaliacao(), s.getGenero(), s.getAtores(), s.getPoster(), s.getSinopse()))
                .collect(Collectors.toList());
    }
}
