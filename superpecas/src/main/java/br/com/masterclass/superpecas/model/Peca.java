package br.com.masterclass.superpecas.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "Pecas")
public class Peca {

    @Id
    @Column(name = "PecaID", nullable = false)
    int pecaID;

    @Column(name = "Nome", nullable = false)
    String nome;

    @Column(name = "Descricao", nullable = false)
    String descricao;

    @Column(name = "NumeroSerie", nullable = false)
    String numeroSerie;

    @Column(name = "Fabricante", nullable = false)
    String fabricante;

    @Column(name = "ModeloCarro", nullable = false)
    String modeloCarro;

    @OneToOne(optional = false)
    @JoinColumn(name = "CarroID", nullable = false)
    Carro carro;
    // @Column(name = "Nome")
    // private String nome;

    // @Column(name = "Descricao")
    // private String descricao;

    // @Column(name = "NumeroSerie", unique = true)
    // private String numeroSerie;

    // @Column(name = "Fabricante")
    // private String fabricante;

    // @Column(name = "ModeloCarro")
    // private String modeloCarro;

    // @ManyToOne(fetch = FetchType.LAZY)
    // @JoinColumn(name = "CarroID", nullable = false)
    // private Carro carro;

    public int getPecaID() {
        return pecaID; 
    }

    public void setPecaID(int pecaID){
        this.pecaID = pecaID;
    }

    public String getNome(){
        return nome;
    }

    public void setNome(String nome){
        this.nome = nome;
    }

    public String getDescricao(){
        return descricao;
    }

    public void setDescricao(String descricao){
        this.descricao = descricao;
    }

    public String getNumeroSerie() {
        return numeroSerie;
    }

    public void setNumeroSerie(String numeroSerie){
        this.numeroSerie = numeroSerie;
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

    public Carro getCarro() {
        return carro;
    }

    public void setCarro(Carro carro) {
        this.carro = carro;
    }
}
