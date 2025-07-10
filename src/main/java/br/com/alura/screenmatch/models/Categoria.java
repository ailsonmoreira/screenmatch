package br.com.alura.screenmatch.models;

public enum Categoria {
        ACAO("Action", "Ação"),
        ROMANCE("Romance", "Romance"),
        COMEDIA("Comedy", "Comédia"),
        DRAMA("Drama", "Drama"),
        CRIME("Crime", "Crime");

        private String categoriaOmdb;
        private String categoriaPtBr;

        Categoria(String categoriaOmdb, String categoriaPtBr){
            this.categoriaOmdb = categoriaOmdb;
            this.categoriaPtBr = categoriaPtBr;
        }

        public static Categoria fromString(String text) {
            for (Categoria categoria : Categoria.values()) {
                if (categoria.categoriaOmdb.equalsIgnoreCase(text)){
                    return categoria;
                }
            }
            throw new IllegalArgumentException("Nenhuma categoria encontrada: " + text);
        }

    public static Categoria fromPtBr(String text) {
        for (Categoria categoria : Categoria.values()) {
            if (categoria.categoriaPtBr.equalsIgnoreCase(text)){
                return categoria;
            }
        }
        throw new IllegalArgumentException("Nenhuma categoria encontrada: " + text);
    }
}
