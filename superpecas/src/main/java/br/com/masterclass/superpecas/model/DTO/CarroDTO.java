package br.com.masterclass.superpecas.model.DTO;

public class CarroDTO {
    private Long carroId;
    private String nomeModelo;
    private String fabricante;
    private String codigoUnico;

    // Getter e setter para carroId
    public Long getCarroId() {
        return carroId;
    }

    public void setCarroId(Long carroId) {
        this.carroId = carroId;
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
