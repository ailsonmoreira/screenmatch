package br.com.alura.screenmatch.controller;

import br.com.alura.screenmatch.dto.SerieDTO;
import br.com.alura.screenmatch.models.Serie;
import br.com.alura.screenmatch.services.SerieServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/series")
public class SerieController {

    @Autowired
    private SerieServices services;

    @GetMapping
    public List<SerieDTO> obterSeries() {
        return services.getAllSeries();
    }

    @GetMapping("/top5")
    public List<SerieDTO> obterTop5Series(){
        return services.getTop5Series();
    }
}
