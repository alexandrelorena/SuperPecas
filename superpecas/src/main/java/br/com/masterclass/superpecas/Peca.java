package br.com.masterclass.superpecas;

import jakarta.persistence.*;

@Entity
@Table(name = "Pecas")
public class Peca {

    @Id
    private Long pecaId;
    private String nome;
    private String descricao;
    @Column(name = "NumeroSerie", nullable = false, unique = true)
    private String numeroSerie;
    private String fabricante;
    private String modeloCarro;

    @ManyToOne
    @JoinColumn(name = "PecaID")
    private Carro peca;


    public Long getPecaId(){
        return pecaId;
    }

    public void setPecaId(Long pecaId){
        this.pecaId = pecaId;
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

    public String getNumeroserie(){
        return numeroSerie;
    }

    public void setNumeroserie(String numeroserie){
        this.numeroSerie = numeroserie;
    }

    public String getFabricante(){
        return fabricante;
    }

    public void setFabricante(String fabricante){
        this.fabricante = fabricante;
    }

    public String getModelocarro() {
        return modeloCarro;
    }

    public void setModeloCarro(String modelocarro){
        this.modeloCarro = modelocarro;
    }
}
