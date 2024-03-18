package br.com.masterclass.superpecas.model.DTO;

public class PecaDTO {

    private Long pecaID;
    private String nome;

    private String descricao;

    private String numeroSerie;

    private String fabricante;

    private String modeloCarro;


    public Long getPecaID() { return pecaID; }

    public void setPecaID(Long pecaID) { this.pecaID = pecaID; }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNumeroSerie() {
        return numeroSerie;
    }

    public void setNumeroSerie(String numeroSerie) {
        this.numeroSerie = numeroSerie;
    }

    public String getDescricao(){
        return descricao;
    }

    public void setDescricao(String descricao){
        this.descricao = descricao;
    }

    public String getFabricante(){
        return fabricante;
    }

    public void setFabricante(String fabricante){
        this.fabricante = fabricante;
    }

    public String getModeloCarro() {
        return modeloCarro;
    }

    public void setModeloCarro(String modeloCarro){
        this.modeloCarro = modeloCarro;
    }

}
