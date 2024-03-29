package br.com.masterclass.superpecas.model.DTO;

public class CarroDTO {
    int carroID;
    String nomeModelo;
    String fabricante;
    String codigoUnico;

    public int getCarroId() {
        return carroID;
    }

    public void setCarroId(int carroID) {
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

    public String getCodigoUnico() {
        return codigoUnico;
    }

    public void setCodigoUnico(String codigoUnico) {
        this.codigoUnico = codigoUnico;
    }
}
