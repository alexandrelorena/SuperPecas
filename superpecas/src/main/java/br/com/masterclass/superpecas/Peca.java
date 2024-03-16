package br.com.masterclass.superpecas;

import jakarta.persistence.*;

@Entity // Anotação para marcar a classe como uma entidade JPA
@Table(name = "Pecas") // Defina o novo nome da tabela aqui
public class Peca {

    @Id // Anotação para marcar o atributo como identificador primário
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Gerado automaticamente
    private Long pecaId;
    private String nome;
    private String descricao;
    @Column(name = "NumeroSerie", nullable = false, unique = true)
    private String numeroSerie;
    private String fabricante;
    private String modeloCarro;

    @ManyToOne
    @JoinColumn(name = "PecaID") // Mapeia a coluna da chave estrangeira
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
