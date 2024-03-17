package br.com.masterclass.superpecas.model;

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

    public void setCarroId(Long carroID) {
        this.carroID = carroID;
    }

    public String getNomeModelo() {
        return nomeModelo;
    }

    public void setNomeModelo(String nomeModelo) {
        this.nomeModelo = nomeModelo;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    public String getCodigoUnico(){
        return codigoUnico;
    }

    public void setCodigoUnico(String codigoUnico){ this.codigoUnico = codigoUnico; }
}