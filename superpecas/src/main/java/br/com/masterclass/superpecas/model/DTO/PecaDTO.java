package br.com.masterclass.superpecas.model.DTO;

import br.com.masterclass.superpecas.model.Carro;

public class PecaDTO {
    int pecaId;
    String nome;
    String descricao;
    String numeroSerie;
    String fabricante;
    String modeloCarro;
    String carroNomeModelo;
    String carroFabricante;
    Integer carroId;
    
    public int getPecaId() {
        return pecaId; 
    }

    public void setPecaId(int pecaId) { 
        this.pecaId = pecaId; 
    }

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

    public String getModeloCarro() {
        return modeloCarro;
    }

    public void setModeloCarro(String modeloCarro){
        this.modeloCarro = modeloCarro;
    }

    public String getFabricante(){
        return fabricante;
    }

    public void setFabricante(String fabricante){
        this.fabricante = fabricante;
    }

    public Integer getCarroId() {
        return carroId;
    }

    public void setCarroId(Integer carroId) {
        this.carroId = carroId;
    }

    public String getCarroNomeModelo() {
        return carroNomeModelo;
    }

    public void setCarroNomeModelo(String carroNomeModelo) {
        this.carroNomeModelo = carroNomeModelo;
    }

    public String getCarroFabricante() {
        return carroFabricante;
    }

    public void setCarroFabricante(String carroFabricante) {
        this.carroFabricante = carroFabricante;
    }
}

