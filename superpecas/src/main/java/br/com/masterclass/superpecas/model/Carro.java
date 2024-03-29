package br.com.masterclass.superpecas.model;

// import jakarta.persistence.*;
// import java.util.List;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;


// import jakarta.persistence.OneToMany;
@Entity
@Table(name = "Carros")
public class Carro {

    // @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    // @Column(name = "CarroID")
    // private Int carroID;
    // private String nomeModelo;
    // private String fabricante;
    // @Column(name = "CodigoUnico", nullable = false, unique = true)
    // private String codigoUnico;
    @Id
    @Column(name = "CarroID", nullable = false)
    int carroID;
    @Column(name = "NomeModelo", nullable = false)
    String nomeModelo;
    @NotNull
    String fabricante;
    @Column(name = "CodigoUnico", nullable = false)
    String codigoUnico;

    // @OneToMany(mappedBy = "carro")
    // private List<Peca> pecas;

    public int getCarroID() {
        return carroID;
    }

    public void setCarroID(int carroID) {
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