package br.com.masterclass.superpecas;

import jakarta.persistence.*;
import org.springframework.stereotype.Service;

@Entity // Anotação para marcar a classe como uma entidade JPA
@Table(name = "Carros") // Defina o novo nome da tabela aqui
public class Carro {

    @Id // Anotação para marcar o atributo como identificador primário
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Gerado automaticamente
    @Column(name = "CarroID")
    private Long carroID;
    private String nomeModelo;
    private String fabricante;
    @Column(name = "CodigoUnico", nullable = false, unique = true)
    private String codigoUnico;


    public Long getCarroId() {
        return carroID;
    }

    public void setCarroId(Long CarroID) {
        this.carroID = CarroID;
    }

    public String getNomeModelo() {
        return nomeModelo;
    }

    public void setNomeModelo(String NomeModelo) {
        this.nomeModelo = NomeModelo;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String Fabricante) {
        this.fabricante = Fabricante;
    }

    public String getCodigoUnico(){
        return codigoUnico;
    }

    public void setCodigoUnico(String CodigoUnico){
        this.codigoUnico = CodigoUnico;
    }
}