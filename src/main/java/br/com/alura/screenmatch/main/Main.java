package br.com.alura.screenmatch.main;

import br.com.alura.screenmatch.models.*;
import br.com.alura.screenmatch.repository.SerieRepository;
import br.com.alura.screenmatch.services.ConsumoAPI;
import br.com.alura.screenmatch.services.DataConversion;

import java.util.*;
import java.util.stream.Collectors;

public class Main {

    private final String ENDERECO = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=225cd208";
    private ConsumoAPI consumo = new ConsumoAPI();
    private DataConversion conversor = new DataConversion();
    private List<SerieData> seriesData = new ArrayList<>();
    private SerieRepository repository;
    private List<Serie> series = new ArrayList<>();

    private Scanner input = new Scanner(System.in);

    private Optional<Serie> serieBusca;

    public Main(SerieRepository repository) {
        this.repository = repository;
    }

    public void exibeMenu(){
        var opcao = -1;
        while(opcao != 0) {
            var menu = """
                    1 - Buscar séries
                    2 - Buscar episódios
                    3 - Listar séries buscadas
                    4 - Buscar série por título
                    5 - Buscar série por ator
                    6 - Top 5 séries
                    7 - Buscar por categoria
                    8 - Filtrar por Número de temporadas e Avaliação
                    9 - Buscar episódio por trecho
                    10 - Top 5 episódios por série
                    11- Buscar Episódio a partir de uma data
                    0 - Sair
                    """;

            System.out.println(menu);
            opcao = input.nextInt();
            input.nextLine();

            switch (opcao) {
                case 1:
                    buscarSerieWeb();
                    break;
                case 2:
                    buscarEpisodioPorSerie();
                    break;
                case 3:
                    listarSeriesBuscadas();
                    break;
                case 4:
                    buscarSeriePorTitulo();
                    break;
                case 5:
                    buscarSeriePorAtor();
                    break;
                case 6:
                    buscarTop5Series();
                    break;
                case 7:
                    buscarPorCategoria();
                    break;
                case 8:
                    filtrarSeriePorTemporadaEAvaliacao();
                    break;
                case 9:
                    buscarEpPorTrecho();
                    break;
                case 10:
                    top5EpPorSerie();
                    break;
                case 11:
                    buscarEpAPartirDeUmaData();
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida");
            }
        }
    }

    private void buscarSerieWeb() {
        SerieData dados = getSerieData();
        Serie serie = new Serie(dados);
        repository.save(serie);
        //seriesData.add(dados);
        System.out.println(dados);
    }

    private SerieData getSerieData() {
        System.out.println("Digite o nome da série para busca");
        var nomeSerie = input.nextLine();
        var json = consumo.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + API_KEY);
        SerieData dados = conversor.obterDados(json, SerieData.class);
        return dados;
    }

    private void buscarEpisodioPorSerie(){

        listarSeriesBuscadas();
        System.out.println("Escolha uma série: ");
        var nomeSerie = input.nextLine();

        Optional<Serie> serieBuscada = repository.findByTituloContainingIgnoreCase(nomeSerie);

        if (serieBuscada.isPresent()) {

            var serieEcontrada = serieBuscada.get();
            List<SeasonData> temporadas = new ArrayList<>();

            for (int i = 1; i <= serieEcontrada.getTotalTemporadas(); i++) {
                var json = consumo.obterDados(ENDERECO + serieEcontrada.getTitulo().replace(" ", "+") + "&season=" + i + API_KEY);
                SeasonData dadosTemporada = conversor.obterDados(json, SeasonData.class);
                temporadas.add(dadosTemporada);
            }
            temporadas.forEach(System.out::println);

            List<Episode> episodios = temporadas.stream()
                    .flatMap(d -> d.episodes().stream()
                            .map(e -> new Episode(d.temporada(), e)))
                    .collect(Collectors.toList());

            serieEcontrada.setEpisodios(episodios);
            repository.save(serieEcontrada);
        } else{
            System.out.println("Série não encontrada");
        }
    }

    private void listarSeriesBuscadas(){
        series = repository.findAll();
        series.stream()
                .sorted(Comparator.comparing(Serie::getGenero))
                .forEach(System.out::println);
    }
    private void buscarSeriePorTitulo() {
        System.out.println("Escolha uma série pelo nome.");
        var nomeSerie = input.nextLine();
        serieBusca = repository.findByTituloContainingIgnoreCase(nomeSerie);

        if (serieBusca.isPresent()) {
            System.out.println("Dados da série: " + serieBusca.get());
        } else {
            System.out.println("Série não encontrada");
        }

    }

    private void buscarSeriePorAtor() {
        System.out.println("Insira o nome do ator: ");
        var nomeAtor = input.nextLine();
        List<Serie> seriesEncontradas = repository.findByAtoresContainingIgnoreCase(nomeAtor);
        System.out.println("Séries em que " + nomeAtor + " trabalhou: ");
        seriesEncontradas.forEach(s ->
                System.out.println(s.getTitulo() + " | Avaliação: " + s.getAvaliacao()));
    }

    private void buscarTop5Series() {
        List<Serie> topSeries = repository.findTop5ByOrderByAvaliacaoDesc();
        topSeries.forEach(s ->
                System.out.println(s.getTitulo() + " | Avaliação: " + s.getAvaliacao()));
    }

    private void buscarPorCategoria() {
        System.out.println("Digite a categoria desejada");
        var nomeGenero = input.nextLine();
        Categoria categoria = Categoria.fromPtBr(nomeGenero);
        List<Serie> seriesPorCategoria = repository.findByGenero(categoria);

        System.out.println("Séries da categoria " + nomeGenero);
        seriesPorCategoria.forEach(System.out::println);
    }

    private void filtrarSeriePorTemporadaEAvaliacao(){
        System.out.println("Buscar séries até quantas temporadas? ");
        var totalTemporadas = input.nextInt();
        System.out.println("A partir de que avalaiação? ");
        var avaliacao = input.nextDouble();
        input.nextLine();

        List<Serie> seriesFiltradas = repository.seriePorTemporadaEAvaliacao(totalTemporadas, avaliacao);
        System.out.println("Séries filtradas: ");
        seriesFiltradas.forEach(s -> System.out.println(s.getTitulo() + "avaliacao - " + s.getAvaliacao()));

    }

    private void buscarEpPorTrecho(){
        System.out.println("Insira um trecho do episódio: ");
        var trechoEp = input.nextLine();
        List<Episode> epsEncontrados = repository.episodiosPorTrecho(trechoEp);
        epsEncontrados.forEach(System.out::println);
    }

    private void top5EpPorSerie(){
        buscarSeriePorTitulo();
        if (serieBusca.isPresent()){
            Serie serie = serieBusca.get();

            List<Episode> topEpisodios= repository.topEpisodiosPorSerie(serie);

            topEpisodios.forEach(e -> System.out.printf("Temporada: %s | Episódio: %s | Título: %s | Avaliação: %s\n", e.getTemporada(),
                    e.getNumeroEpisodio(), e.getTitulo(), e.getAvaliacao()));
        }

    }

    private void buscarEpAPartirDeUmaData(){
        buscarSeriePorTitulo();
        if (serieBusca.isPresent()){
            Serie serie = serieBusca.get();
            System.out.println("Digite o ano limite de lançamento");
            int anoLancamento = input.nextInt();
            input.nextLine();

            List<Episode> episodesAno = repository.epPorSerieEPorAno(serie, anoLancamento);
            episodesAno.forEach(System.out::println);
        }
    }

}