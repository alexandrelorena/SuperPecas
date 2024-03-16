package br.com.masterclass.superpecas;

import jakarta.persistence.*;

@Entity
@Table(name = "Carros")
public class Carro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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