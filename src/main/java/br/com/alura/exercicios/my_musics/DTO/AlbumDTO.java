package br.com.alura.exercicios.my_musics.DTO;

public class AlbumDTO {

    private String nome;
    private Long idITunes;
    private String capa;
    private Double preco;
    private String anoLancamento;

    public AlbumDTO(String nome, String capa, Double preco, String anoLancamento, Long idITunes) {
        this.nome = nome;
        this.capa = capa;
        this.preco = preco;
        this.anoLancamento = anoLancamento;
        this.idITunes = idITunes;
    }

    public AlbumDTO() {}

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCapa() {
        return capa;
    }

    public void setCapa(String capa) {
        this.capa = capa;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public String getAnoLancamento() {
        return anoLancamento;
    }

    public void setAnoLancamento(String anoLancamento) {
        this.anoLancamento = anoLancamento;
    }

    public Long getIdITunes() {
        return idITunes;
    }
}
