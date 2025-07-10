package br.com.alura.screenmatch.services;

public interface IDataConverter {

    <T> T obterDados(String json, Class<T> classe);

}
