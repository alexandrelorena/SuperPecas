package br.com.masterclass.superpecas.model.DTO;

public class CarroDTO {
    int carroID;
    String nomeModelo;
    String fabricante;
    String codigoUnico;

    // Getter e setter para carroId
    public int getCarroId() {
        return carroID;
    }

    public void setCarroId(int carroID) {
        this.carroID = carroID;
    }

    // Getter e setter para nomeModelo
    public String getNomeModelo() {
        return nomeModelo;
    }

    public void setNomeModelo(String nomeModelo) {
        this.nomeModelo = nomeModelo;
    }

    // Getter e setter para fabricante
    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    // Getter e setter para codigoUnico
    public String getCodigoUnico() {
        return codigoUnico;
    }

    public void setCodigoUnico(String codigoUnico) {
        this.codigoUnico = codigoUnico;
    }
}
