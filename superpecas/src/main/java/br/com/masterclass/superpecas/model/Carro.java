package br.com.masterclass.superpecas.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "Carros")
public class Carro {
    @Id
    @Column(name = "CarroID", nullable = false)
    int carroId;
    @Column(name = "NomeModelo", nullable = false)
    String nomeModelo;
    @NotNull
    String fabricante;
    @Column(name = "CodigoUnico", nullable = false)
    String codigoUnico;

    public int getCarroId() {
        return carroId;
    }

    public void setCarroId(int carroId) {
        this.carroId = carroId;
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
